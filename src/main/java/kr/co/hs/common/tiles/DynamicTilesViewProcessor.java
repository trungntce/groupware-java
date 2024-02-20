package kr.co.hs.common.tiles;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.hs.common.util.StringUtil;
import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.TilesException;
import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.request.servlet.ServletRequest;
import org.apache.tiles.request.servlet.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.JstlUtils;
import org.springframework.web.servlet.support.RequestContext;

public class DynamicTilesViewProcessor {

    public static final String BEAN_NAME = "__BEAN_NAME";

    final Logger logger = LoggerFactory.getLogger(DynamicTilesViewProcessor.class);

    private static final String FILTER = "_";

    @SuppressWarnings("unused")
    private String derivedDefinitionName = null;

    private String tilesDefinitionName = "default";
    private String tilesBodyAttributeName = "body";
    private String tilesDefinitionDelimiter = "/";

    private ServletRequest servletRequest = null;
    public void getServletRequest(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response){
        ApplicationContext applicationContext = ServletUtil.getApplicationContext(servletContext);
        ServletRequest servletRequest = new ServletRequest(applicationContext, request, response);
        this.servletRequest = servletRequest;
    }

    public void setTilesDefinitionName(String tilesDefinitionName) {
        if( !tilesDefinitionName.equals(this.tilesDefinitionName) )
            this.tilesDefinitionName = tilesDefinitionName;
    }

    public void setTilesBodyAttributeName(String tilesBodyAttributeName) {
        this.tilesBodyAttributeName = tilesBodyAttributeName;
    }

    public void setTilesDefinitionDelimiter(String tilesDefinitionDelimiter) {
        this.tilesDefinitionDelimiter = tilesDefinitionDelimiter;
    }

    protected void renderMergedOutputModel(String beanName, String url, ServletContext servletContext, HttpServletRequest request,
                                           HttpServletResponse response, TilesContainer container) throws Exception {

        getServletRequest(servletContext,request,response);
        JstlUtils.exposeLocalizationContext(new RequestContext(request, servletContext));

        String definitionName = startDynamicDefinition(beanName, url, request, response, container);
        getServletRequest(servletContext,request,response);
        container.render(definitionName, this.servletRequest);

        endDynamicDefinition(definitionName, beanName, request, response, container);
    }

    protected String startDynamicDefinition(String beanName, String url, HttpServletRequest request, HttpServletResponse response,
                                            TilesContainer container) throws TilesException {

        String definitionName = processTilesDefinitionName(beanName, container, request, response);

        if (!definitionName.equals(beanName)) {
            Attribute attr = new Attribute();
            //attr.setName(tilesBodyAttributeName);

            attr.setValue(url);

            AttributeContext attributeContext = container.startContext(this.servletRequest);
            attributeContext.putAttribute(tilesBodyAttributeName, attr);

            logger.debug("URL used for Tiles body.  url='" + url + "'.");
        }

        return definitionName;
    }

    /**
     * Closes the temporary Tiles definition.
     */
    protected void endDynamicDefinition(String definitionName, String beanName, HttpServletRequest request, HttpServletResponse response,
                                        TilesContainer container) {
        if (!definitionName.equals(beanName)) {

            container.endContext(this.servletRequest);
        }
    }
    protected String processTilesDefinitionName(String beanName, TilesContainer container, HttpServletRequest request,
                                                HttpServletResponse response) throws TilesException {

        container.isValidDefinition(beanName, this.servletRequest);

        request.setAttribute(BEAN_NAME, beanName);

        if( request.getAttribute("__template") != null )
            return tilesDefinitionDelimiter + request.getAttribute("__template");

        String result = null;

        StringBuilder sb = new StringBuilder();
        int lastIndex = beanName.lastIndexOf("/");
        boolean rootDefinition = false;

        if (StringUtils.hasLength(tilesDefinitionDelimiter)) {
            sb.append(tilesDefinitionDelimiter);
        }

        if (lastIndex == -1) {
            rootDefinition = true;
        } else {
            String path = beanName.substring(0, lastIndex);

            if (StringUtils.hasLength(tilesDefinitionDelimiter)) {
                path = StringUtils.replace(path, "/", tilesDefinitionDelimiter);
            }

            sb.append(path);

            if (StringUtils.hasLength(tilesDefinitionDelimiter)) {
                sb.append(tilesDefinitionDelimiter);
            }
        }

        sb.append(tilesDefinitionName);

        if (container.isValidDefinition(sb.toString(), this.servletRequest)) {
            result = sb.toString();
        } else if (!rootDefinition) {
            String root = null;

            if (StringUtils.hasLength(tilesDefinitionDelimiter)) {
                root = tilesDefinitionDelimiter;
            }

            root += tilesDefinitionName;

            String firstName = StringUtil.getFirst(beanName, "/");
            if(container.isValidDefinition(FILTER + firstName, this.servletRequest)) {
                return FILTER + firstName;
            }

            if (container.isValidDefinition(root, servletRequest)) {
                result = root;
            } else {
                throw new TilesException("No defintion of found for " + "'" + root + "'" + " or '" + sb.toString() + "'");
            }
        }
        derivedDefinitionName = result;

        return result;
    }

}
