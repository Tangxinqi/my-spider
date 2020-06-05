package org.github.txq.spider.xml;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import java.io.InputStream;

/**
 * @author tangxinqi
 * @date 2020/4/10 01:40
 */
public class MyEntityResolver implements EntityResolver {

    private static final String REQUEST_DTD = "template.request.dtd";
    private static final String RESPONSE_DTD = "template.response.dtd";

    @Override
    public InputSource resolveEntity(String publicId, String systemId) {
        String dtd;
        if (systemId.endsWith(REQUEST_DTD)) {
            dtd = REQUEST_DTD;
        } else if (systemId.endsWith(RESPONSE_DTD)) {
            dtd = RESPONSE_DTD;
        } else {
            throw new IllegalArgumentException("template`s systemId[" + systemId + "] can not be distinguished,please check you template!");
        }
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(dtd);
        InputSource inputSource = new InputSource(inputStream);
        inputSource.setPublicId(publicId);
        inputSource.setSystemId(systemId);
        return inputSource;
    }
}
