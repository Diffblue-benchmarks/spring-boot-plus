/**
 * Copyright 2019-2029 geekidea(https://github.com/geekidea)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.geekidea.springbootplus.resource.web.interceptor;

import io.geekidea.springbootplus.common.api.ApiResult;
import io.geekidea.springbootplus.core.SpringBootPlusProperties;
import io.geekidea.springbootplus.util.HttpServletResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 下载拦截器
 * @author geekidea
 * @date 2019/8/21
 * @since 1.2.2-RELEASE
 */
@Slf4j
public class DownloadInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private SpringBootPlusProperties springBootPlusProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果访问的不是控制器,则跳出,继续执行下一个拦截器
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 访问路径
        String url = request.getRequestURI();
        // 访问全路径
        String fullUrl = request.getRequestURL().toString();

        // 未启用资源访问时，返回错误消息
        if (!springBootPlusProperties.getInterceptorConfig().getDownloadConfig().isEnabled()){
            log.error("下载已关闭，非法下载：{}",fullUrl);
            HttpServletResponseUtil.printJSON(response, ApiResult.fail("非法下载"));
            return false;
        }

        // 下载拦截器，业务处理代码
        log.info("DownloadInterceptor...");

        // 访问token，如果需要，可以设置参数，进行鉴权
        String token = request.getParameter("token");

        log.info("url:{}",url);
        log.info("fullUrl:{}",fullUrl);
        log.info("token:{}",token);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 记录实际下载日志...
    }
}
