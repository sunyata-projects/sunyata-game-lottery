//package org.sunyata.game.business;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import org.sunyata.core.util.Md5;
//import org.sunyata.game.client.AnyClientManager;
//import org.sunyata.game.service.ClientServerInfo;
//import org.sunyata.game.service.UserCacheService;
//import org.sunyata.game.contract.ProductCodes;
//import org.sunyata.game.currency.models.FundAccountType;
//import org.sunyata.game.currency.services.CurrencyDomainService;
//import org.sunyata.game.exceptions.ExistsForMySelfException;
//import org.sunyata.game.exceptions.NoExistProductException;
//import org.sunyata.game.service.ServerLocation;
//import org.sunyata.game.lock.LockService;
//import org.sunyata.game.lock.MutiLock;
//import org.sunyata.game.order.models.Order;
//import org.sunyata.game.order.models.OrderItem;
//import org.sunyata.game.order.models.OrderStatusType;
//import org.sunyata.game.order.models.Product;
//import org.sunyata.game.order.services.OrderDomainService;
//import org.sunyata.game.order.services.ProductDomainService;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by leo on 17/11/16.
// */
//@Component
//public class ApplicationService {
//    @Autowired
//    OrderDomainService orderDomainService;
//
//    @Autowired
//    ProductDomainService productDomainService;
//
//    @Autowired
//    CurrencyDomainService currencyDomainService;
//
//    @Autowired
//    RoomDomainService roomDomainService;
//
//    @Autowired
//    ServerLocation serverLocation;
//
//    @Autowired
//    LockService lockService;
//    @Autowired
//    UserCacheService userCacheService;
//
//    @Autowired
//    FundLockKeys fundLockKeys;
//    @Autowired
//    RoomLockKeys lockKeys;
//    @Autowired
//    AnyClientManager anyClientManager;
//
//    @Transactional(rollbackFor = Exception.class)
//    public String createRoom(Integer userId, String majiangSceneServiceName, RoomConfigInfo roomConfigInfo) throws
//            Exception {
//
//        MutiLock createRoomLock = lockService.getlock(lockKeys.getCreateRoomLockKey(String.valueOf(userId)));
//        createRoomLock.acquire(5, TimeUnit.SECONDS);
//        try {
//            //检查是否有为自己开的房间
//            boolean existsForMySelf = roomDomainService.existsForMySelf(userId);
//            if (existsForMySelf) {
//                throw new ExistsForMySelfException("用户" + userId + "已经为自己开过房");
//            }
//            String productCode = ProductCodes.CreateRoomProductCode;
//            //执行买入交易,扣款
//            Order order = buy(userId, productCode, 1);
//            //开始执行创建房间逻辑
//            //插入房间记录
//            ClientServerInfo randomClientServerInfo = serverLocation.getRandomClientServerInfo(majiangSceneServiceName);
//            String roomCheckId = roomDomainService.createRoom(userId, order.getId(), randomClientServerInfo.getGatewayServerId
//                            (),
//                    roomConfigInfo);
//            //更新订单状态为成功
//            orderDomainService.updateOrderStatus(order.getId(), OrderStatusType.Success, "");
//            //至此房间创建完毕业,用户可以join了
//            //发送创建成功消息给用户
//            return roomCheckId;
//        } finally {
//            createRoomLock.release();
//        }
//
//    }
//
//    public Order buy(Integer userId, String productCode, int quantity) throws Exception {
//        String orderId = generatorOrderId(1001);
//        List<OrderItem> orderItems = new ArrayList<>();
//        Product product = productDomainService.loadProduct(productCode);
//        if (product == null) {
//            throw new NoExistProductException("不存在的商品:" + productCode);
//        }
//        OrderItem item = new OrderItem();
//        item.setProductId(product.getId()).setOrderId(orderId).setPrice(product.getPrice()).setQuantity(quantity);
//        orderItems.add(item);
//        Order order = orderDomainService.createOrder(orderId, userId, 1, "", "", "", OrderStatusType.Init, orderItems);
//        //扣货币
//        FundAccountType fundAccountType = FundAccountType.from(product.getMoneyType());
//        MutiLock fundLock = lockService.getlock(fundLockKeys.getFundLockKey(String.valueOf(userId), fundAccountType));
//        fundLock.acquire(5, TimeUnit.SECONDS);
//        try {
//            currencyDomainService.createFundWithoutLock(orderId, String.valueOf(userId), "", fundAccountType, 1,
//                    order.getTotalPrice(),
//                    "-" +
//                            order.getTotalPrice(), "");
//        } finally {
//            fundLock.release();
//        }
//        return order;
//    }
//
//    private void processOrder(Order order) {
//        for (OrderItem item : order.getOrderItems()) {
//            Product product = productDomainService.loadProductById(item.getProductId());
//            processOrder(product.getProductType(), item.getQuantity(), order.getUserId());
//        }
//    }
//
//    private void processOrder(int productType, int quantity, String userId) {
//        try {
//            if (productType == 1) {//创建房间
//
//            }
//        } catch (Exception ex) {
//
//        }
//        //更新订单
//    }
//
//    public String generatorOrderId(Integer cpId) {
//        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//        Date nowtime = new Date();
//        return cpId + "_" + df.format(nowtime) + "_" + new Md5().randomString(8);
//    }
//}
