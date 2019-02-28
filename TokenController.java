import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;

@SessionScoped
@Named
public class TokenController implements Serializable {

  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private String token;
  private String originalUrl;

  public TokenController() {
  }

  public String redirectToOriginalUrl() {
    final String outcome = originalUrl + "?faces-redirect=true";
    originalUrl = null;
    return outcome;
  }

  public boolean isValid() {
    return token != null && token.length() > 0;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getOriginalUrl() {
    return originalUrl;
  }

  public void setOriginalUrl(String originalUrl) {
    this.originalUrl = originalUrl;
  }

  @Override
  public String toString() {
    return "TokenController{" +
        "token='" + token + '\'' +
        ", originalUrl='" + originalUrl + '\'' +
        '}';
  }
}
