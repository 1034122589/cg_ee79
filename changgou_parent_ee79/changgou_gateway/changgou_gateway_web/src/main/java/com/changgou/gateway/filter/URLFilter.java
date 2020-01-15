package com.changgou.gateway.filter;

/**
 * 路径过滤
 */
public class URLFilter {

    /**
     * 购物车订单微服务都需要用户登录，必须携带令牌，所以所有路径都过滤,订单微服务需要过滤的地址
     */
    public static String orderFilterPath = "/user/login";

    /**
     * 检查路径是否需要权限校验
     *
     * @param uri
     * @return true:需要校验 false:不需校验
     */
    public static boolean needNoAuthorize(String uri) {
        String[] urls = orderFilterPath.replace("**", "").split(",");
        for (String url : urls) {
            if (uri.startsWith(url)) {
                return false;
            }
        }
        return true;
    }

}
