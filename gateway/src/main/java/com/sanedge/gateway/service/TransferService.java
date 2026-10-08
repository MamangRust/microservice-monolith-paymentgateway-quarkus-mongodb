package com.sanedge.gateway.service;

import com.sanedge.gateway.dto.TransferDto;
import io.smallrye.mutiny.Uni;

public interface TransferService {
    Uni<TransferDto.ApiResponsePaginationTransfer> listTransfers(int page, int size, String search);
    Uni<TransferDto.ApiResponsePaginationTransferDeleteAt> findActiveTransfers(int page, int size, String search);
    Uni<TransferDto.ApiResponsePaginationTransferDeleteAt> findTrashedTransfers(int page, int size, String search);
    Uni<TransferDto.ApiResponseTransfer> getTransfer(String id);
    Uni<TransferDto.ApiResponseTransfers> findTransfersFrom(String cardNumber);
    Uni<TransferDto.ApiResponseTransfers> findTransfersTo(String cardNumber);
    Uni<TransferDto.ApiResponseTransfer> createTransfer(TransferDto.CreateRequest body);
    Uni<TransferDto.ApiResponseTransfer> updateTransfer(String id, TransferDto.UpdateRequest body);
    Uni<TransferDto.ApiResponseTransferDeleteAt> deleteTransfer(String id);
    Uni<TransferDto.ApiResponseTransferDeleteAt> trashTransfer(String id);
    Uni<TransferDto.ApiResponseTransferDeleteAt> restoreTransfer(String id);
    Uni<TransferDto.SimpleResponse> deleteTransferPermanent(String id);
    Uni<TransferDto.SimpleResponse> restoreAllTransfers();
    Uni<TransferDto.SimpleResponse> deleteAllTransfers();

    Uni<TransferDto.ApiResponseTransferMonthAmount> findMonthlyAmounts(int year);
    Uni<TransferDto.ApiResponseTransferYearAmount> findYearlyAmounts(int year);
    Uni<TransferDto.ApiResponseTransferMonthAmount> findMonthlyAmountsFromCard(int year, String cardNumber);
    Uni<TransferDto.ApiResponseTransferMonthAmount> findMonthlyAmountsToCard(int year, String cardNumber);
    Uni<TransferDto.ApiResponseTransferYearAmount> findYearlyAmountsFromCard(int year, String cardNumber);
    Uni<TransferDto.ApiResponseTransferYearAmount> findYearlyAmountsToCard(int year, String cardNumber);

    Uni<TransferDto.ApiResponseTransferMonthStatusSuccess> findMonthlyStatusSuccess(int year, int month);
    Uni<TransferDto.ApiResponseTransferYearStatusSuccess> findYearlyStatusSuccess(int year);
    Uni<TransferDto.ApiResponseTransferMonthStatusFailed> findMonthlyStatusFailed(int year, int month);
    Uni<TransferDto.ApiResponseTransferYearStatusFailed> findYearlyStatusFailed(int year);

    Uni<TransferDto.ApiResponseTransferMonthStatusSuccess> findMonthlyStatusSuccessByCard(int year, int month, String cardNumber);
    Uni<TransferDto.ApiResponseTransferYearStatusSuccess> findYearlyStatusSuccessByCard(int year, String cardNumber);
    Uni<TransferDto.ApiResponseTransferMonthStatusFailed> findMonthlyStatusFailedByCard(int year, int month, String cardNumber);
    Uni<TransferDto.ApiResponseTransferYearStatusFailed> findYearlyStatusFailedByCard(int year, String cardNumber);
}
