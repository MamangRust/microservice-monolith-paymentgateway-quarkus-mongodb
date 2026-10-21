import logging
import json
import os
import threading
from kafka import KafkaConsumer

# Canonical topic set produced by the F3 outbox relay / stats-backfill and
# consumed by stats-writer. ai-security shares these same topics as a separate
# consumer group (ai-security-group) so it can learn from every payment event.
DEFAULT_STATS_TOPICS = [
    'stats.payment.transaction.event',
    'stats.payment.topup.event',
    'stats.payment.transfer.event',
    'stats.payment.withdraw.event',
    'stats.payment.saldo.event',
    'stats.payment.merchant.event',
    'stats.payment.card.event',
]


class TransactionConsumer(threading.Thread):
    def __init__(self, bootstrap_servers=['localhost:9092'], topics=None):
        super().__init__()
        self.bootstrap_servers = bootstrap_servers
        # Allow overriding the topic list via STATS_TOPICS (comma-separated) to
        # stay consistent with stats-writer's configuration convention.
        self.topics = topics if topics is not None else self._topics_from_env()
        self.daemon = True
        self.running = True

    @staticmethod
    def _topics_from_env():
        raw = os.environ.get('STATS_TOPICS')
        if raw:
            return [t.strip() for t in raw.split(',') if t.strip()]
        return list(DEFAULT_STATS_TOPICS)

    def run(self):
        logging.info(f"Connecting to Kafka at {self.bootstrap_servers}...")
        try:
            consumer = KafkaConsumer(
                *self.topics,
                bootstrap_servers=self.bootstrap_servers,
                auto_offset_reset='earliest',
                enable_auto_commit=True,
                group_id='ai-security-group',
                value_deserializer=lambda x: json.loads(x.decode('utf-8'))
            )

            logging.info(f"Subscribed to {len(self.topics)} security topics: {self.topics}")

            for message in consumer:
                if not self.running:
                    break
                event = message.value
                logging.info(f"Received security event from {message.topic}: {event.get('event_id')}")
                self.process_event(event)
        except Exception as e:
            logging.error(f"Kafka consumer error: {e}")

    def process_event(self, event):
        # In a real scenario, this would update user risk profiles
        # or store data for retraining
        logging.info(f"Processing event for ML learning: {event.get('transaction_id')}")

    def stop(self):
        self.running = False
