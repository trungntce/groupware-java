package kr.co.hs.common.tiles;

import org.apache.tiles.TilesContainer;
import org.apache.tiles.access.TilesAccess;
import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.request.servlet.ServletUtil;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DynamicTilesView extends AbstractUrlBasedView {

    final DynamicTilesViewProcessor dynamicTilesViewProcessor = new DynamicTilesViewProcessor();

    /**
     * Main template name. The default is 'mainTemplate'.
     *
     * @param tilesDefinitionName
     *            Main template name used to lookup definitions.
     */
    public void setTilesDefinitionName(String tilesDefinitionName) {
        dynamicTilesViewProcessor.setTilesDefinitionName(tilesDefinitionName);
    }

    /**
     * Tiles body attribute name. The default is 'body'.
     *
     * @param tilesBodyAttributeName
     *            Tiles body attribute name.
     */
    public void setTilesBodyAttributeName(String tilesBodyAttributeName) {
        dynamicTilesViewProcessor.setTilesBodyAttributeName(tilesBodyAttributeName);
    }

    /**
     * Sets Tiles definition delimiter. For example, instead of using the
     * request 'info/about' to lookup the template definition
     * 'info/mainTemplate', the default delimiter of '.' would look for
     * '.info.mainTemplate'
     *
     * @param tilesDefinitionDelimiter
     *            Optional delimiter to replace '/' in a url.
     */
    public void setTilesDefinitionDelimiter(String tilesDefinitionDelimiter) {
        dynamicTilesViewProcessor.setTilesDefinitionDelimiter(tilesDefinitionDelimiter);
    }

    /**
     * Renders output using Tiles.
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        ServletContext servletContext = getServletContext();
        ApplicationContext tilesContext = ServletUtil.getApplicationContext(servletContext);
        TilesContainer container = TilesAccess.getContainer( tilesContext);

        if (container == null) {
            throw new ServletException("Tiles container is not initialized. "
                + "Have you added a TilesConfigurer to your web application context?");
        }
        exposeModelAsRequestAttributes(model, request);
        dynamicTilesViewProcessor.renderMergedOutputModel(getBeanName(), getUrl(), servletContext, request, response, container);
    }
}
