package org.github.txq.spider.jtbc;

import com.google.common.io.CharStreams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.github.txq.spider.jtbc.exception.JtbcDataSourceException;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

/**
 * @author tangxinqi
 * @date 2020/4/4 23:16
 */
@Slf4j
public class ClassPathResourceTemplateSource implements TemplateSource {

    @Override
    public void init() {
        if (log.isDebugEnabled()) {
            log.debug("ClassPathResourceTemplateSource init!");
        }
    }

    @Override
    public String getTemplate(String name, String version) {
        if (StringUtils.isBlank(name)) {
            return StringUtils.EMPTY;
        }
        String templateName = "./" + (name.endsWith(".xml") ? name : name + ".xml");
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(templateName)))) {
            return CharStreams.toString(reader);
        } catch (Exception e) {
            throw new JtbcDataSourceException("read template [name = " + name + "] error from classpath!", e);
        }
    }

    @Override
    public void destroy() {
        if (log.isDebugEnabled()) {
            log.debug("destroy ClassPathResourceTemplateSource!");
        }
    }
}
