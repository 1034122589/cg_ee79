package com.changgou.gateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局过滤器
 */
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {
    /**
     * 令牌头名字
     */
    private static final String AUTHORIZE_TOKEN = "Authorization";
    /**
     * 用户登录跳转地址
     */
    private static String USER_LOGIN_URL = null;
    @Value("${spring.cloud.client.ip-address}")
    private  String ip;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        // 获取请求的uri
        String path = request.getURI().getPath();
        if (!URLFilter.needNoAuthorize(path) || path.startsWith("/items/**") ||"/api/user/reset".equals(path) || "/api/my/login".equals(path)) {
            // 放行
            Mono<Void> filter = chain.filter(exchange);
            return filter;
        }
        // 获取头文件的令牌信息
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        // 头文件没有从请求参数获取
        if (StringUtils.isEmpty(token)) {
            token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);

        }
        // 请求参数没有从cookie中获取
        if (StringUtils.isEmpty(token)) {
            // 从cookie 获取
            HttpCookie cookie = request.getCookies().getFirst(AUTHORIZE_TOKEN);
            if (cookie != null) {
                token = cookie.getValue();
            }
        }

        // 如果token 为空,重定向到登录页面
        if (StringUtils.isEmpty(token)) {
            USER_LOGIN_URL = "http://"+ip+"/login.html";
            // 携带上次的访问地址
            return needAuthorization(USER_LOGIN_URL+"?from="+request.getHeaders().getFirst("Referer"),exchange);
        }

        try {
            //向头文件中存储令牌数据
            request.mutate().header(AUTHORIZE_TOKEN, "Bearer " + token);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        // 放行
        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 响应设置
     * @param url
     * @param exchange
     * @return
     */
    public Mono<Void> needAuthorization(String url, ServerWebExchange exchange){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.SEE_OTHER);
        response.getHeaders().set("Location",url);
        return exchange.getResponse().setComplete();
    }
}
