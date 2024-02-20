package kr.co.hs.common.tiles;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.mobile.device.site.SitePreferenceUtils;
import org.springframework.mobile.device.util.ResolverUtils;
import org.springframework.mobile.device.view.LiteDeviceDelegatingViewResolver;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ViewResolver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestAttributeLiteDeviceDelegatingViewResolver extends LiteDeviceDelegatingViewResolver{

	//the name of the attribute to put in scope
	protected String attributeName = "mDeviceType";
	//the value of the attribute #attributeName for Mobile
	protected String mobileValue = "m";
	protected String tabletValue = "t";
	protected String normalValue = "";

	public RequestAttributeLiteDeviceDelegatingViewResolver(ViewResolver delegate) {
		super(delegate);
	}


	@Override
	protected String getDeviceViewNameInternal(String viewName) {
		RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
		Assert.isInstanceOf(ServletRequestAttributes.class, attrs);
		HttpServletRequest request = ((ServletRequestAttributes) attrs).getRequest();
		Device device = DeviceUtils.getCurrentDevice(request);
		SitePreference sitePreference = SitePreferenceUtils.getCurrentSitePreference(request);
		String resolvedViewName = viewName;
		String attributeValue = normalValue;

		log.debug("resolvedViewName =======> "+resolvedViewName);
		log.debug("attributeValue =======> "+attributeValue);
		log.debug("ResolverUtils.isNormal(device, sitePreference) =======> "+ResolverUtils.isNormal(device, sitePreference));
		log.debug("ResolverUtils.isMobile(device, sitePreference) =======> "+ResolverUtils.isMobile(device, sitePreference));

		if (ResolverUtils.isNormal(device, sitePreference)) {
			resolvedViewName = getNormalPrefix() + viewName + getNormalSuffix();
			attributeValue = normalValue;
		} else if (ResolverUtils.isMobile(device, sitePreference)) {
			resolvedViewName = getMobilePrefix() + viewName + getMobileSuffix();

			attributeValue = mobileValue;
		} else if (ResolverUtils.isTablet(device, sitePreference)) {
			resolvedViewName = getTabletPrefix() + viewName + getTabletSuffix();
			attributeValue = tabletValue;
		}
		request.setAttribute(attributeName, attributeValue);

		return resolvedViewName;
	}

	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * Specifies the name of the attribute to be put into the request
	 * scope. Defaults to 'm.deviceType'
	 * @param attributeName
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getMobileValue() {
		return mobileValue;
	}

	/**
	 * Specifies the value to be set to the AttributeName when the
	 * detected device is Mobile
	 * @param mobileValue
	 */
	public void setMobileValue(String mobileValue) {
		this.mobileValue = mobileValue;
	}

	public String getTabletValue() {
		return tabletValue;
	}

	/**
	 * Specifies the value to be set to the AttributeName when the
	 * detected device is Tablet
	 * @param tabletValue
	 */
	public void setTabletValue(String tabletValue) {
		this.tabletValue = tabletValue;
	}

	public String getNormalValue() {
		return normalValue;
	}

	/**
	 * Specifies the value to be set to the AttributeName when the
	 * detected device is Normal (i.e. not-mobile and not-tablet)
	 * @param normalValue
	 */
	public void setNormalValue(String normalValue) {
		this.normalValue = normalValue;
	}
}
