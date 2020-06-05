package org.github.txq.spider.spring.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * @author tangxinqi
 * @date 2019/11/13 19:50
 */
@Getter
@Setter
@ToString
public class OnChangeTemplateEvent extends ApplicationEvent {

    private String templateName;

    private String version;

    public OnChangeTemplateEvent(String templateName, String version) {
        super(templateName + version);
        this.templateName = templateName;
        this.version = version;
    }
}
