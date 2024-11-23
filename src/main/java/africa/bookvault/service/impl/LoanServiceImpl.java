package africa.bookvault.service.impl;

import africa.bookvault.exception.ResourceNotFoundException;
import africa.bookvault.models.Book;
import africa.bookvault.models.Loan;
import africa.bookvault.models.Member;
import africa.bookvault.repository.LoanRepository;
import africa.bookvault.service.IBookService;
import africa.bookvault.service.ILoanService;
import africa.bookvault.service.IMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoanServiceImpl implements ILoanService {

    private final LoanRepository loanRepository;
    private final IBookService bookService;
    private final IMemberService memberService;

    @Transactional
    public Loan loanBook(UUID bookId, UUID memberId) {
        Book book = bookService.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        Member member = memberService.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        Loan loan = new Loan();
        loan.setMemberId(member.getId());
        loan.setLoanDate(LocalDate.now());
        return loanRepository.save(loan);
    }

    @Override
    public List<Loan> getLoansByMember(UUID memberId) {
        return loanRepository.findByMemberId(memberId);
    }

    @Override
    public List<Loan> getLoansByBook(UUID bookId) {
        return null;
    }
}
