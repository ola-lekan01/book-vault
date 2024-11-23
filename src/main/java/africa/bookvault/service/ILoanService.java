package africa.bookvault.service;

import africa.bookvault.models.Loan;

import java.util.List;
import java.util.UUID;

public interface ILoanService {

    Loan loanBook(UUID bookId, UUID memberId);
    List<Loan> getLoansByMember(UUID memberId);
    List<Loan> getLoansByBook(UUID bookId);
}
