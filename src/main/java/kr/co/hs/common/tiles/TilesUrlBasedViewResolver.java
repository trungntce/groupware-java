package kr.co.hs.common.tiles;


import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

public class TilesUrlBasedViewResolver extends org.springframework.web.servlet.view.UrlBasedViewResolver {

    private String tilesDefinitionName = null;
    private String tilesBodyAttributeName = null;
    private String tilesDefinitionDelimiter = null;

    /**
 * Main template name.
 * @uml.property  name="tilesDefinitionName"
 */
    public void setTilesDefinitionName(String tilesDefinitionName) {
            this.tilesDefinitionName = tilesDefinitionName;
    }

    /**
 * Tiles body attribute name.
 * @uml.property  name="tilesBodyAttributeName"
 */
    public void setTilesBodyAttributeName(String tilesBodyAttributeName) {
            this.tilesBodyAttributeName = tilesBodyAttributeName;
    }

    /**
 * Sets Tiles definition delimiter.
 * @uml.property  name="tilesDefinitionDelimiter"
 */
    public void setTilesDefinitionDelimiter(String tilesDefinitionDelimiter) {
            this.tilesDefinitionDelimiter = tilesDefinitionDelimiter;
    }

    /*
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
    	

    	if( viewName.startsWith("redirect:") )
    		return super.resolveViewName(viewName, locale);

    	if( locale.getCountry().equals("KR") )
    		viewName = viewName + "_kr";

    	return super.resolveViewName(viewName, locale);
    }

    @Override
    protected View loadView(String viewName, Locale locale) throws Exception {
    	
    	View view = super.loadView(viewName, locale);

        if(AbstractUrlBasedView.class.isAssignableFrom(view.getClass())) {
            if( getApplicationContext().getResource(((AbstractUrlBasedView) view).getUrl()).exists() )
            	return view;
            else
            	return viewName.indexOf("_") < 0 ? null : loadView( viewName.substring(0, viewName.indexOf("_") ), locale);
        }
        else
            return view;
    }
    */

    /**
     * Does everything the <code>UrlBasedViewResolver</code> does and
     * also sets some Tiles specific values on the view.
     *
     * @param viewName the name of the view to build
     * @return the View instance
     * @throws Exception if the view couldn't be resolved
     * @see #loadView(String, java.util.Locale)
     */
    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        AbstractUrlBasedView view = super.buildView(viewName);

        // if DynamicTilesView, set tiles specific values
        if (view instanceof DynamicTilesView) {
            DynamicTilesView dtv = (DynamicTilesView)view;

            if (StringUtils.hasLength(tilesDefinitionName)) {
                    dtv.setTilesDefinitionName(tilesDefinitionName);
            }

            if (StringUtils.hasLength(tilesBodyAttributeName)) {
                    dtv.setTilesBodyAttributeName(tilesBodyAttributeName);
            }

            if (tilesDefinitionDelimiter != null) {
                    dtv.setTilesDefinitionDelimiter(tilesDefinitionDelimiter);
            }
        }

        return view;
    }

}
