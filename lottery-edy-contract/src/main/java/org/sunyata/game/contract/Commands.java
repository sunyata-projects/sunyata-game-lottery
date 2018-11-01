//package org.sunyata.game.contract;
//
///**
// * Created by leo on 17/11/9.
// */
//public class Commands {
//    /*******客户端开始（其它7位长度的是下行消息）*******/
//    //登录
//    public final static String login = "51001";//登录请求
//    public final static String loginRet = "5100101";//登录信息下行
//    /************** 房间**************/
//    public final static String createRoom = "63001";//创建房间
//    public final static String createRoomRet = "6300101";//创建房间下行
//
//    public final static String joinRoom = "63002";//加入房间
//    public final static String joinRoomRet = "6300201";//加入房间下行
//
//    public final static String joinGame = "63004";//加入游戏
//    public final static String gameRoomInfo = "6300401";//加入游戏下行
//    /*************房间相关的消息编号**************/
//
//    /************** 麻将相关的消息编号**************/
//    public final static String optFaPaiRet = "71001";//发牌后,玩家打牌发送此消息
//    public final static String optOutRet = "71002";//用户吃碰杠后,打牌发送此消息
//    public final static String optCPGHRet = "71003";//用户吃碰杠和,发送此消息
//
//    public final static String userOffline = "5100201";//用户离线消息同步下行
//    public final static String optFaPai = "7100101";//给用户发牌（即抓牌），包括杠牌的发牌（抓牌）
//    public final static String optCPGH = "7100201";//玩家可以吃碰杠和的信息，下行
//    public final static String optOut = "7100301";//通知当前用户出牌(在吃碰后)
//    public final static String tingPai = "7100401";//通知当前用户听牌(在吃碰后)
//    public final static String syncOpt = "7100501";//同步操作
//    public final static String syncOptTime = "7100601";//通知用户开始计时(房间所有用户)
//    public final static String majiangChapterInfo = "7100701";//一局麻将信息,在局开始时发送给客户端
//    public final static String gameChapterEnd = "7100801";//本局结束消息
//    public final static String gameChapterStart = "71008";//当前局开始
//    /************** 麻将相关的消息编号**************/
//
//    /********用户信息同步******/
//    public final static String gameUserInfo = "6400101";
//    /*******用户信息同步******/
//
//    /*******客户端结束*******/
//
//    /******系统内部消息**********/
//    public final static String joinRoomToScene = "63003";
//    public final static String joinGameToScene = "63005";
//    /******系统内部消息**********/
//    //    public final static String loginSuccessEvent = "51003";
////    public final static String loginEvent = "51601";
//    public final static String logout = "51002";//
//
//    /******Event*********/
//    public final static String userOfflineEvent = "51004";//
//    public final static String userJoinGameEvent = "51005";//
//    /******Event*********/
//
//    public final static String createOrder = "62001";
//
//    /***************************/
//    /***************************/
//
//    //game inner gateway service
//    //game gateway service
//    //game test service
//
//    public final static String testCommand = "40001";
//    public final static String testCommandRet = "4000101";
//    public final static String testCommand2Ret = "4000102";
//
//    public final static String errorCommand = "99001";
//    public final static String notice = "99002";
//}
