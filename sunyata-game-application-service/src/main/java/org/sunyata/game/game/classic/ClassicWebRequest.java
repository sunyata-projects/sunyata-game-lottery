package org.sunyata.game.game.classic;

import org.springframework.stereotype.Component;
import org.sunyata.game.AbstractWebRequest;
import org.sunyata.game.contract.GameIds;

/**
 * @author leo
 */
@Component("WebRequest_" + GameIds.Classic)
public class ClassicWebRequest extends AbstractWebRequest {
}
