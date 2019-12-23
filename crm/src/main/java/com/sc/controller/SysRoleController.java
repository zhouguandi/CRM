package com.sc.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.sc.entity.Result;
import com.sc.entity.SysGongsiinfo;
import com.sc.entity.SysRole;
import com.sc.service.impl.SysRoleServiceImpl;

@Controller
@RequestMapping("/rap")
public class SysRoleController {

	@Autowired
	SysRoleServiceImpl sysRoleServiceImpl;
	
	//====================角色信息列表========================
	/**
	 * 角色和权限列表
	 * @param mav
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/selectAllRoleAndPower.do") 
	public ModelAndView selectAllRoleAndPower(ModelAndView mav, @RequestParam(defaultValue="1")Integer pageNum, @RequestParam(defaultValue="5")Integer pageSize) {
		PageInfo<SysRole> list = sysRoleServiceImpl.selectAllRoleAndPower(pageNum, pageSize);
		mav.addObject("RP", list);
		mav.setViewName("sys/RoleAndPower");
		return mav;
	}
	
	
	@RequestMapping("/goUpdateRle.do")
	public ModelAndView updateRoleList(ModelAndView mav, SysRole sysRole) {
		List<SysRole> selectAllRole = sysRoleServiceImpl.selectAllRole();
		SysRole selectOne = sysRoleServiceImpl.selectOne(sysRole.getRid());
		mav.addObject("allR", selectAllRole);
		mav.addObject("RPOne", selectOne);
		mav.setViewName("sys/updateRole");
		return mav;
	}
	
	
	@RequestMapping("/updateRle.do")
	@ResponseBody
	public Result updateRoleOne(SysRole s){
		System.out.println(s);
		System.out.println("进入修改..");
		s.setLasttime(new Date());
		sysRoleServiceImpl.updateRole(s);
		return new Result(200, "修改成功！");
	}
	
	@RequestMapping("/goAddRole.do")
	public ModelAndView goAddRole(ModelAndView mav, SysRole sysRole) {
		List<SysRole> selectAllRole = sysRoleServiceImpl.selectAllRole();
		mav.addObject("allR", selectAllRole);
		mav.setViewName("sys/addRole");
		return mav;
	}
	
	@RequestMapping("/addRole.do")
	@ResponseBody
	public Result addRole(SysRole s){
		s.setLasttime(new Date());
		sysRoleServiceImpl.addRole(s);
		return new Result(200, "添加成功！");
	}
	
	@RequestMapping("/delRole.do")
	@ResponseBody
	public Result delRole(SysRole s){
		System.out.println("开始删除！"+s.getRid());
		sysRoleServiceImpl.delRole(s.getRid());
		return new Result(200, "删除成功！");
	}
	
	@RequestMapping("/delAllRole.do")
	@ResponseBody
	public void gsdelete(ModelAndView mav,String aa){//aa	字符串格式：1,3,2,4...
		System.out.println("公司删除！"+aa);
		
		String[] RIDArr = aa.split(",");

		for (String rid : RIDArr) {
			SysRole selectOne = sysRoleServiceImpl.selectOne(new BigDecimal(rid));
			sysRoleServiceImpl.delRole(selectOne.getRid());
		}
	}
}
