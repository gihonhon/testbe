package com.binar.finalproject.flightticket.service.impl;

import com.binar.finalproject.flightticket.dto.NotificationRequest;
import com.binar.finalproject.flightticket.utility.PNRGenerator;
import com.binar.finalproject.flightticket.dto.OrderDetailResponse;
import com.binar.finalproject.flightticket.dto.OrderRequest;
import com.binar.finalproject.flightticket.dto.OrderResponse;
import com.binar.finalproject.flightticket.model.*;
import com.binar.finalproject.flightticket.repository.*;
import com.binar.finalproject.flightticket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private TravelerListRepository travelerListRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private AirportsRepository airportsRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private RouteRepository routeRepository;

    @Override
    public OrderResponse addOrder(OrderRequest orderRequest) {
        try{
            Optional<Users> users = userRepository.findById(orderRequest.getUserId());
            Optional<PaymentMethods> paymentMethods = paymentMethodRepository.findById(orderRequest.getPaymentId());

            if(users.isPresent())
            {
                if(paymentMethods.isPresent())
                {
                    Orders orders = orderRequest.toOrders(users.get(), paymentMethods.get());
                    Float totalPrice = 0f;
                    List<TravelerList> allTravelerList = new ArrayList<>();
                    List<UUID> allSchedulesId = new ArrayList<>();
                    List<Schedules> allSchedules = new ArrayList<>();
                    List<String> departureCity = new ArrayList<>();
                    List<String> arrivalCity = new ArrayList<>();

                    for (UUID schedulesId: orderRequest.getScheduleId()) {
                        Optional<Schedules> schedules = scheduleRepository.findById(schedulesId);
                        if(schedules.isPresent())
                        {
                            Optional<Routes> routes = routeRepository.findById(schedules.get().getRoutesSchedules().getRouteId());
                            if(routes.isPresent()){
                                departureCity.add(routes.get().getDepartureCity());
                                arrivalCity.add(routes.get().getArrivalCity());
                                allSchedulesId.add(schedules.get().getScheduleId());
                                allSchedules.add(schedules.get());
                                totalPrice += schedules.get().getPrice();
                            }
                            else
                                return null;
                        }
                        else
                            return null;
                    }

                    for (UUID travelerListId: orderRequest.getTravelerListId()){
                        Optional<TravelerList> travelerList = travelerListRepository.findById(travelerListId);
                        if(travelerList.isPresent())
                            allTravelerList.add(travelerList.get());
                        else
                            return null;
                    }

                    orders.setTotalTicket(allSchedulesId.size());
                    orders.setTotalPrice(totalPrice);
                    orders.setScheduleOrders(allSchedules);
                    orders.setTravelerListsOrder(allTravelerList);
                    orders.setStatus("WAITING");
                    orderRepository.save(orders);
                    return OrderResponse.build(orders, allSchedulesId, departureCity, arrivalCity);
                }
                else
                    return null;
            }
            else
                return null;
        }catch (Exception exception)
        {
            return null;
        }
    }

    @Override
    public OrderResponse updateOrder(OrderRequest orderRequest, UUID orderId) {
        Optional<Orders> isOrders = orderRepository.findById(orderId);
        String message = null;
        if (isOrders.isPresent())
        {
            Orders orders = isOrders.get();

            if(orderRequest.getStatus().equals("ACCEPTED")
                    && orders.getPnrCode() == null)
            {
                orders.setStatus(orderRequest.getStatus());
                orders.setPnrCode(PNRGenerator.generatePNR());
                NotificationRequest notificationRequest = new NotificationRequest("Order : " + orders.getOrderId().toString(),
                        "Pesanan kamu sudah dikonfirmasi");
                Optional<Users> users = userRepository.findById(orders.getUsersOrder().getUserId());
                if(users.isPresent())
                {
                    Notification notification = notificationRequest.toNotification(users.get());
                    notificationRepository.save(notification);
                }
                else
                    return null;
            }
            else
                orders.setStatus(orderRequest.getStatus());

            orders.getScheduleOrders().clear();

            Float totalPrice = 0f;
            List<UUID> allSchedulesId = new ArrayList<>();
            List<Schedules> allSchedules = new ArrayList<>();
            List<String> departureCity = new ArrayList<>();
            List<String> arrivalCity = new ArrayList<>();
            List<TravelerList> allTravelerList = new ArrayList<>();

            for (UUID schedulesId: orderRequest.getScheduleId()) {
                Optional<Schedules> schedules = scheduleRepository.findById(schedulesId);
                if(schedules.isPresent())
                {
                    Optional<Routes> routes = routeRepository.findById(schedules.get().getRoutesSchedules().getRouteId());
                    if(routes.isPresent()){
                        departureCity.add(routes.get().getDepartureCity());
                        arrivalCity.add(routes.get().getArrivalCity());
                        allSchedulesId.add(schedules.get().getScheduleId());
                        allSchedules.add(schedules.get());
                        totalPrice += schedules.get().getPrice();
                    }
                    else
                        return null;
                }
                else
                    return null;
            }

            for (UUID travelerListId: orderRequest.getTravelerListId()){
                Optional<TravelerList> travelerList = travelerListRepository.findById(travelerListId);
                if(travelerList.isPresent())
                    allTravelerList.add(travelerList.get());
                else
                    return null;
            }

            orders.setTotalTicket(allSchedulesId.size());
            orders.setTotalPrice(totalPrice);
            orders.setScheduleOrders(allSchedules);
            orders.setTravelerListsOrder(allTravelerList);

            Optional<Users> users = userRepository.findById(orderRequest.getUserId());
            if (users.isPresent())
                orders.setUsersOrder(users.get());
            else
                message = "User with this id doesnt exist";

            Optional<PaymentMethods> paymentMethods = paymentMethodRepository.findById(orderRequest.getPaymentId());
            if (paymentMethods.isPresent())
                orders.setPaymentMethodsOrder(paymentMethods.get());
            else
                message = "Payment with this id doesnt exist";

            if (message != null)
                return null;
            else {
                try {
                    orderRepository.save(orders);
                    return OrderResponse.build(orders, orderRequest.getScheduleId(), departureCity, arrivalCity);
                }
                catch (Exception exception){
                    return null;
                }
            }
        }
        else
            return null;
    }

    @Override
    public List<OrderResponse> getAllOrder() {
        List<Orders> allOrder = orderRepository.findAll();
        return orderToOrderResponseList(allOrder);
    }

    @Override
    public List<OrderDetailResponse> getAllOrderAdmin() {
        List<Orders> allOrder = orderRepository.findAll();
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        for (Orders orders: allOrder) {
            List<Schedules> allSchedules = orders.getScheduleOrders();
            List<TravelerList> allTravelerList = orders.getTravelerListsOrder();
            List<String> travelerListName = new ArrayList<>();
            List<UUID> allTravelerListId = new ArrayList<>();
            List<UUID> allScheduleListId = new ArrayList<>();
            for (TravelerList travelerList: allTravelerList) {
                String travelerName = travelerList.getTitle().replace(" ", "") + ". " + travelerList.getFirstName() + " "
                        + travelerList.getLastName();
                travelerListName.add(travelerName);
                allTravelerListId.add(travelerList.getTravelerId());
            }
            Optional<Users> users = userRepository.findById(orders.getUsersOrder().getUserId());
            Optional<PaymentMethods> paymentMethods = paymentMethodRepository.findById(orders.getPaymentMethodsOrder().getPaymentId());

            Airplanes airplanes = null;
            Airports departureAirports = null;
            Airports arrivalAirports = null;
            Routes routes = null;
            Schedules schedule = null;

            for (Schedules schedules : allSchedules){
                allScheduleListId.add(schedules.getScheduleId());
                schedule = schedules;
                routes = schedules.getRoutesSchedules();
                airplanes = schedules.getAirplanesSchedules();
                departureAirports = airportsRepository.findByAirportName(routes.getDepartureAirport());
                arrivalAirports = airportsRepository.findByAirportName(routes.getArrivalAirport());
            }

            if(paymentMethods.isPresent() && users.isPresent()){
                OrderDetailResponse orderDetailResponse = OrderDetailResponse.build(orders,
                        airplanes, departureAirports, arrivalAirports, routes, schedule, paymentMethods.get());
                orderDetailResponse.setUserId(users.get().getUserId());
                orderDetailResponse.setFullName(users.get().getFullName());
                orderDetailResponse.setEmail(users.get().getEmail());
                orderDetailResponse.setScheduleId(allScheduleListId);
                orderDetailResponse.setPaymentId(paymentMethods.get().getPaymentId());
                orderDetailResponse.setTravelerListName(travelerListName);
                orderDetailResponse.setTravelerListId(allTravelerListId);
                orderDetailResponses.add(orderDetailResponse);
            }
            else
                return Collections.emptyList();
        }
        return orderDetailResponses;
    }

    @Override
    public List<OrderResponse> getAllOrderByUserId(UUID userId) {
        List<Orders> allOrder = orderRepository.findAllOrderByUserId(userId);
        return orderToOrderResponseList(allOrder);
    }

    @Override
    public List<OrderResponse> getAllOrderByUserIdAndStatus(UUID userId, String status) {
        List<Orders> allOrder = orderRepository.findHistoryByStatus(userId, status);
        return orderToOrderResponseList(allOrder);
    }

    @Override
    public List<OrderResponse> getAllOrderByPaymentId(Integer paymentId) {
        List<Orders> allOrder = orderRepository.findAllOrderByPaymentId(paymentId);
        return orderToOrderResponseList(allOrder);
    }

    @Override
    public List<OrderDetailResponse> getOrderDetails(UUID orderId) {
        Optional<Orders> isOrders = orderRepository.findById(orderId);
        if(isOrders.isPresent())
        {
            Orders orders = isOrders.get();
            List<Schedules> allSchedules = orders.getScheduleOrders();
            List<TravelerList> allTravelerList = orders.getTravelerListsOrder();
            List<String> travelerListName = new ArrayList<>();
            for (TravelerList travelerList: allTravelerList) {
                String travelerName = travelerList.getTitle().replace(" ", "") + ". " + travelerList.getFirstName() + " "
                        + travelerList.getLastName();

                travelerListName.add(travelerName);
            }

            List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
            Optional<PaymentMethods> paymentMethods = paymentMethodRepository.findById(orders.getPaymentMethodsOrder().getPaymentId());
            for (Schedules schedules : allSchedules) {
                Routes routes = schedules.getRoutesSchedules();
                Airplanes airplanes = schedules.getAirplanesSchedules();
                Airports departureAirports = airportsRepository.findByAirportName(routes.getDepartureAirport());
                Airports arrivalAirports = airportsRepository.findByAirportName(routes.getArrivalAirport());
                if(paymentMethods.isPresent()){
                    OrderDetailResponse orderDetailResponse = OrderDetailResponse.build(orders,
                            airplanes, departureAirports, arrivalAirports, routes, schedules, paymentMethods.get());
                    orderDetailResponse.setTravelerListName(travelerListName);
                    orderDetailResponses.add(orderDetailResponse);
                }
                else
                    return Collections.emptyList();
            }
            return orderDetailResponses;
        }
        else
            return Collections.emptyList();
    }

    private List<OrderResponse> orderToOrderResponseList(List<Orders> allOrder)
    {
        List<OrderResponse> allOrderResponse = new ArrayList<>();
        for (Orders orders : allOrder)
        {
            List<Schedules> schedules = orders.getScheduleOrders();
            List<UUID> schedulesId = new ArrayList<>();
            List<String> departureCity = new ArrayList<>();
            List<String> arrivalCity = new ArrayList<>();

            for (Schedules schedule : schedules) {
                assert false;
                Optional<Routes> routes = routeRepository.findById(schedule.getRoutesSchedules().getRouteId());
                departureCity.add(routes.get().getDepartureCity());
                arrivalCity.add(routes.get().getArrivalCity());
                schedulesId.add(schedule.getScheduleId());
            }

            OrderResponse orderResponse = OrderResponse.build(orders, schedulesId, departureCity, arrivalCity);
            allOrderResponse.add(orderResponse);
        }
        return allOrderResponse;
    }

}
