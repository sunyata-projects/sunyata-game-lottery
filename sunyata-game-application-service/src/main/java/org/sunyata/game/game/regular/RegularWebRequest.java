package org.sunyata.game.game.regular;

import org.springframework.stereotype.Component;
import org.sunyata.game.AbstractWebRequest;
import org.sunyata.game.contract.GameIds;

/**
 * @author leo
 */
@Component("WebRequest_" + GameIds.Regular)
public class RegularWebRequest extends AbstractWebRequest {
}
