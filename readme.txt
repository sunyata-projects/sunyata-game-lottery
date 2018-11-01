game-edy-gateway-service:22010
game-edy-inner-gateway-service:22020
game-edy-application-service:22030

game-edy-interact-service:22040
game-edy-sales-system-service:22050
game-edy-mock-engine-service:22060
game-edy-ai-service:22070

UserLocationInfo
UserCacheInfo
ServerCommandInfo


第二局开始有准备

平台提供以下接口
    1赛事列表接口
           方向：cp->平台
           字段：主赛事id 赛事名称 赛事级别 大师分限制 最低报名人数 人满or定时 奖励分信息 服务费信息
                如果是定时
                    开始时间类型：1-周期：每天开始时间-结束时间 每X分钟一场
                                2-固定时间段：开始-结束 开始-结束 开始-结束
                如果是人满意：
                    开始时间类型：2-固定时间段：开始-结束 开始-结束 开始-结束
    2比赛报名接口
            方向：cp->平台
            字段：主赛事id playerId
            说明：报名接口调用成功后，平台在比赛结束时正常扣除赛事服务费
            返回：
                子赛事Id
    3赛事开始(单局)通知：
            方向：平台->cp
            字段：子赛事id，开始时间
            说明：在比赛开始前会调用，调用完成后，才开始比赛
    4比赛单局结算
            方向：平台->cp
            字段：赛事id 轮次 [{"playerId":"lclc","score":"9","time":"32423434ms"},
                              {"playerId":"lclc","score":"9","time":"32423434ms"},
                              {"playerId":"lclc","score":"9","time":"32423434ms"}]
    5比赛单轮结算
            方向：平台->cp
            字段：赛事id 轮次 [{"playerId":"lclc","score":"9","time":"32423434ms"},
                              {"playerId":"lclc","score":"9","time":"32423434ms"},
                              {"playerId":"lclc","score":"9","time":"32423434ms"}]
    6比赛最终结算
            方向：平台->cp
            字段：赛事id 轮次 [{"playerId":"lclc","score":"9","time":"32423434ms"},
                              {"playerId":"lclc","score":"9","time":"32423434ms"},
                              {"playerId":"lclc","score":"9","time":"32423434ms"}]
客户端接入
    客户端url:http://open.majiang.cmsa.cn/?appId=102003&playerId=tg33023994949949&sign=39dsmm9043sdrsdlfk
    生成签名的方式：sign = md5(appid+playerId+时间戳精确定到5分+securityKey）
赛事引擎
    赛事定义：
        赛事名称 赛事级别（A,B，C等）最少开赛人数 大师分限制   比赛类型（人人，人机）    场次数量    奖励说明
    针对每一轮次，可定义如下信息
    比赛类型
        人机赛：
            同等牌力（单选）：牌力选项：牌力难度
            人机复式（单选）：AI难度选择（高中低）
        人人赛移位规则：
                编排规则1:复式移位蛇形排位
                编排规则2:豪威尔移位
                编排规则n:编排规则说明
        牌库定义：
            本轮打几幅牌:
            所使用的牌库：
                           1系统默认牌库
                           2自定义牌库
        晋级定义：1、前X名
                 2、前xP
                 3、无晋级（只有一轮或最后一轮）
        奖励定义：
                1、大师分
                2、奖金
                3、其它
        赛事时间编排，定义每轮次开始时间
                可每一轮单独指定，也可单独定义第一轮和非第一轮的开赛时间
                第一轮开始时间定义：
                         如果是定时
                             开始时间类型：1-周期：每天开始时间-结束时间 每X分钟一场
                                          2-固定时间段：开始-结束 开始-结束 开始-结束
                         如果是人满意：
                             满多少名开赛
                             开始时间类型：2-固定时间段：开始-结束 开始-结束 开始-结束
                中间轮次开始时间定义：1前一轮结束后H小时M分钟，2指定时间 3、手工开始
                最后轮次开始时间定义：1前一轮结束后H小时M分钟，2指定时间 3、手工开始
                可单独指定第X轮次时间定义：1前一轮结束后H小时M分钟，2指定时间 3、手工开始
        参赛选手资格：定义每轮次可参赛选手
                            1白名单(手工添加)
                            2上一轮次的晋级名单
                            3、无限制
                            4、大师分要求