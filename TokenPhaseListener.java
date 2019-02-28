import org.apache.deltaspike.jsf.api.listener.phase.JsfPhaseListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

@JsfPhaseListener
public class TokenPhaseListener implements PhaseListener {

  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  public TokenPhaseListener() {
  }

  @Inject
  private TokenController tokenController;

  @Override
  public void beforePhase(final PhaseEvent event) {
    final FacesContext facesContext = event.getFacesContext();
    final ExternalContext externalContext = facesContext.getExternalContext();
    final String redirect = "/token.xhtml";
    final String originalUrl = externalContext.getRequestServletPath();
    if (!originalUrl.equals(redirect)) {
      LOG.info("token=" + tokenController);
      if (!tokenController.isValid()) {
        tokenController.setOriginalUrl(originalUrl);
        try {
          externalContext.redirect(externalContext.getRequestContextPath() + redirect);
        } catch (IOException e) {
          LOG.error("Can't redirect to token input site.");
        }
        facesContext.responseComplete();
      }
    }
  }

  @Override
  public void afterPhase(final PhaseEvent event) {
  }

  @Override
  public PhaseId getPhaseId() {
    return PhaseId.RENDER_RESPONSE;
  }
}
