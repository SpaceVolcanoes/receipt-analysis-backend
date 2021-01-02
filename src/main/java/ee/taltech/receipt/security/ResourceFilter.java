package ee.taltech.receipt.security;

import ee.taltech.receipt.service.CustomerService;
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
import java.nio.file.AccessDeniedException;


@Order(1)
@AllArgsConstructor
public class ResourceFilter implements Filter {

    private final UserSessionService userSessionService;
    private final CustomerService customerService;
    private final EntryService entryService;
    private final ReceiptService receiptService;

    @Override
    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain
    ) throws IOException, ServletException {
        SessionUser user = userSessionService.getUser();
        String userName = user.getUsername();

        String[] uri = ((HttpServletRequest) request).getRequestURI().split("/");
        String resourceOwner = getResourceOwner(uri[2], uri[1]);

        if (!resourceOwner.equals(userName) && !user.getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("Access to " + uri[1] + " " + uri[2] + " denied");
        }
        chain.doFilter(request, response);
    }

    private String getResourceOwner(String resourceId, String resourceType) throws AccessDeniedException {
        if (resourceType.equals("customers")) {
            return customerService.findById(Long.parseLong(resourceId)).getEmail();
        }
        if (resourceType.equals("entries")) {
            return entryService.findById(Long.parseLong(resourceId)).getReceipt().getCustomer().getEmail();
        }
        if (resourceType.equals("files")) {
            return receiptService.findByFilename(resourceId).getCustomer().getEmail();
        }
        if (resourceType.equals("receipts")) {
            return receiptService.findById(Long.parseLong(resourceId)).getCustomer().getEmail();
        }
        throw new AccessDeniedException("Unable to parse resource");
    }
}
