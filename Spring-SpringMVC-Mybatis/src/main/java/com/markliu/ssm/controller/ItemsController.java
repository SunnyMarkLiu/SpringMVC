package com.markliu.ssm.controller;

import com.markliu.ssm.mapper.ItemsMapper;
import com.markliu.ssm.po.Items;
import com.markliu.ssm.po.ItemsCustom;
import com.markliu.ssm.po.ItemsCustomQueryVo;
import com.markliu.ssm.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Author: markliu
 * Time  : 16-8-23 下午4:21
 */
@Controller
@RequestMapping(value = "/items")
public class ItemsController {

	private ItemsService itemsService;

	@Autowired
	public void setItemsService(ItemsService itemsService) {
		this.itemsService = itemsService;
	}

	private ItemsMapper itemsMapper;

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	public void setItemsMapper(ItemsMapper itemsMapper) {
		this.itemsMapper = itemsMapper;
	}

	@RequestMapping(value = "/query_items", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView queryItems(ItemsCustomQueryVo itemsCustomQueryVo) throws Exception {

		List<ItemsCustom> itemsCustomList = itemsService.getAllItemsLikeName(itemsCustomQueryVo);

		for (ItemsCustom itemsCustom : itemsCustomList) {
			System.out.println(itemsCustom.toString());
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("itemsCustomList", itemsCustomList);
		modelAndView.setViewName("items/itemsList");
		return modelAndView;
	}

	/**
	 * 跳转到编辑 Items 的页面
	 */
	@RequestMapping(value = "/edit_items")
	public ModelAndView editItems(@RequestParam Integer id) throws Exception {
		ItemsCustom itemsCustom = itemsService.getItemsCustomById(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("itemsCustom", itemsCustom);
		modelAndView.setViewName("items/editItems");
		return modelAndView;
	}

	@RequestMapping(value = "/query_items2", method = {RequestMethod.POST, RequestMethod.GET})
	public String queryItems2(Model model) throws Exception {

		List<ItemsCustom> itemsCustomList = itemsService.getAllItemsLikeName(null);

		model.addAttribute("itemsCustomList", itemsCustomList);
		return "items/itemsList";
	}

	/**
	 * 跳转到编辑 Items 的页面
	 */
	@RequestMapping(value = "/edit_items2")
	public String editItems2(@RequestParam Integer id, Model model) throws Exception {
		ItemsCustom itemsCustom = itemsService.getItemsCustomById(id);
		model.addAttribute("itemsCustom", itemsCustom);
		return "items/editItems";
	}

	/**
	 * 更新 Items
	 */
	@RequestMapping(value = "/update_items")
	public String updateItems(ItemsCustom itemsCustom) throws Exception {

		System.out.println("itemsCustom : " + itemsCustom.toString());
		itemsService.updateItems(itemsCustom.getId(), itemsCustom);
		// 服务器内部内部请求转发, 地址栏 url 不变
		// return "forward:/items/query_items2";
//		return "forward:query_items2";

		// 请求重定向, 地址栏 url 变化
		 return "redirect:/items/query_items2";

	}

	@RequestMapping("/delete_items")
	public String deleteItems(Integer[] itemsId) throws Exception {
		// 调用 Service 删除 Items
		for (Integer id : itemsId) {
			System.out.println(id);
		}
		return "forward:/items/query_items";
	}
}
