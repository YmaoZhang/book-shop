package com.book.bookshop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.book.bookshop.entity.*;
import com.book.bookshop.service.AddressService;
import com.book.bookshop.service.BookService;
import com.book.bookshop.service.CartService;
import com.book.bookshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description: 订单控制器
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private CartService cartService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private BookService bookService;

    /**
     * 确认购物车中订单
     */
    @RequestMapping("/confirm")
    public String confirm(String ids, HttpSession session, Model model){
        //查询记录
        List<CartVo> cartVos = cartService.findCartByIds(ids.split(","));//重要

        //获取当前用户的收货地址
        QueryWrapper<Address> addressQueryWrapper = new QueryWrapper<>();
        User user = (User) session.getAttribute("user");
        addressQueryWrapper.eq("user_id",user.getId());
        List<Address> addressList = addressService.list(addressQueryWrapper);

        //将购买的商品信息添加到session中
        session.setAttribute("cartVos",cartVos);

        model.addAttribute("list",cartVos);
        model.addAttribute("addressList",addressList);
        return "confirm_order";
    }

    /**
     * 提交订单
     */
    @RequestMapping("/commitOrder")
    public String commitOrder(Integer addrId, HttpSession session){
        List<CartVo> cartVos = (List<CartVo>) session.getAttribute("cartVos");
        String flag = orderService.buy(cartVos,addrId,session);
        if(flag.equals("success")){
            //跳转至订单列表页
            return "redirect:/order/list";
        }else {
            return "redirect:/book/index";
        }
    }


    /**
     * 确认立即购买的订单
     */
    @RequestMapping("/buyNowConfirm")
    public String buyNowConfirm( Integer bookId,Integer count,HttpSession session, Model model){
        //封装立即购买商品信息
         Book book= bookService.getById(bookId);
         CartVo cartVo=new CartVo();
         cartVo.setBookId(bookId);
        cartVo.setBookName(book.getName());
        cartVo.setCount(count);
        cartVo.setImgUrl(book.getImgUrl());
        cartVo.setNewPrice(book.getNewPrice());
        //获取当前用户的收货地址
        QueryWrapper<Address> addressQueryWrapper = new QueryWrapper<>();
        User user = (User) session.getAttribute("user");
        addressQueryWrapper.eq("user_id",user.getId());
        List<Address> addressList = addressService.list(addressQueryWrapper);

        //将立即购买的商品信息添加到session中
        session.setAttribute("cart",cartVo);
        model.addAttribute("cart",cartVo);
        model.addAttribute("addressList",addressList);
        return "confirm_order_buyNow";
    }

    /**
     * 提交立即购买的订单
     */
    @RequestMapping("/buyNowCommitOrder")
    public String buyNowCommitOrder(Integer addrId, HttpSession session){
        CartVo cart = (CartVo) session.getAttribute("cart");
        String flag = orderService.buyNow(cart,addrId,session);
        if(flag.equals("success")){
            //跳转至订单列表页
            return "redirect:/order/list";
        }else {
            return "redirect:/book/index";
        }
    }



    /**
     * 显示用户订单列表
     */
    @RequestMapping("/list")
    public String list(){
        return "order_list";
    }

    /**
     *
     * @param session
     * @param model
     * @return 获取用户订单信息
     */
    @RequestMapping("/getOrderListData")
    public String getOrderListData(HttpSession session, OrderQueryVo orderQueryVo, Model model){
        User user = (User) session.getAttribute("user");
        List<Order> orders = orderService.findUserOrder(user.getId(),orderQueryVo);
        model.addAttribute("orders",orders);
        model.addAttribute("pre",orderQueryVo.getPage() -1);
        model.addAttribute("next",orderQueryVo.getPage() + 1);
        model.addAttribute("cur",orderQueryVo.getPage());
        model.addAttribute("pages",orderService.findUserOrderPages(user.getId(),orderQueryVo));
        model.addAttribute("pageSize",orderQueryVo.getPageSize());
        return "orderData";
    }

}
