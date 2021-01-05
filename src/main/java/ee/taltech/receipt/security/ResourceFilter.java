package ee.taltech.receipt.security;

import ee.taltech.receipt.exception.ResourceAccessDeniedException;
import ee.taltech.receipt.service.EntryService;
import ee.taltech.receipt.service.ReceiptService;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Order(1)
@AllArgsConstructor
public class ResourceFilter implements Filter {

    private final UserSessionService userSessionService;
    private final EntryService entryService;
    private final ReceiptService receiptService;

    @Override
    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain
    ) throws IOException, ServletException {
        SessionUser user = userSessionService.getUser();
        if (user == null) {
            chain.doFilter(request, response);
            return;
        }

        if (user.getRole().equals(Role.ADMIN) || ((HttpServletRequest) request).getMethod().equals("POST")) {
            chain.doFilter(request, response);
            return;
        }
        String[] uri = ((HttpServletRequest) request).getRequestURI().split("/");
        if (uri.length < 3) {
            chain.doFilter(request, response);
            return;
        }

        Long userId = user.getId();
        Long resourceOwner = getResourceOwnerId(uri[2], uri[1]);

        if (resourceOwner.equals(userId)) {
            chain.doFilter(request, response);
            return;
        }
        throw new ResourceAccessDeniedException("Access to " + uri[1] + " " + uri[2] + " denied");
    }

    private Long getResourceOwnerId(String resourceId, String resourceType) {
        if (resourceType.equals("customers")) {
            return Long.parseLong(resourceId);
        }
        if (resourceType.equals("entries")) {
            return entryService.findById(Long.parseLong(resourceId)).getReceipt().getCustomer().getId();
        }
        if (resourceType.equals("receipts")) {
            return receiptService.findById(Long.parseLong(resourceId)).getCustomer().getId();
        }
        throw new ResourceAccessDeniedException("Unable to parse resource");
    }

}
