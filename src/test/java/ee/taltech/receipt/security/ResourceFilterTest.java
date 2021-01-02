package ee.taltech.receipt.security;

import ee.taltech.receipt.model.Customer;
import ee.taltech.receipt.model.Entry;
import ee.taltech.receipt.model.Receipt;
import ee.taltech.receipt.service.EntryService;
import ee.taltech.receipt.service.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResourceFilterTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;
    private SessionUser user;

    @Mock
    private UserSessionService userSessionService;

    @Mock
    private EntryService entryService;

    @Mock
    private ReceiptService receiptService;

    @InjectMocks
    private ResourceFilter filter;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
        user = mock(SessionUser.class);
        when(userSessionService.getUser()).thenReturn(user);
    }

    @Test
    void doFilterThrowsErrorIfIdsMismatch() throws IOException, ServletException {
        when(user.getId()).thenReturn(2L);
        when(user.getRole()).thenReturn(Role.USER);
        when(request.getRequestURI()).thenReturn("/customers/1/receipts");

        Throwable thrown = catchThrowable(() -> filter.doFilter(request, response, chain));
        assertThat(thrown)
            .isInstanceOf(AccessDeniedException.class)
            .hasMessage("Access to customers 1 denied");
    }

    @Test
    void doFilterPassesIfIdsMatch() throws IOException, ServletException {
        when(user.getId()).thenReturn(5L);
        when(user.getRole()).thenReturn(Role.USER);
        when(request.getRequestURI()).thenReturn("/entries/8");
        when(entryService.findById(8L)).thenReturn(
            new Entry().setReceipt(
                new Receipt().setCustomer(
                    new Customer().setId(5L)
                )
            )
        );

        filter.doFilter(request, response, chain);
        verify(chain).doFilter(request, response);
    }

    @Test
    void doFilterPassesIfUserIsAdmin() throws IOException, ServletException {
        when(user.getRole()).thenReturn(Role.ADMIN);

        filter.doFilter(request, response, chain);
        verify(chain).doFilter(request, response);
    }

}
