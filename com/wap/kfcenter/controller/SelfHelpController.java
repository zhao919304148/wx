package com.wap.kfcenter.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wap.kfcenter.model.ZzkfWxSelfHelp;
import com.wap.kfcenter.service.KfSelfService;

@Controller
@RequestMapping(value={"/zzkf"})
public class SelfHelpController {
	
	@Autowired(required = false)
	private KfSelfService kfSelfService = null;
	
	/**
	 * 
			* 描述:问题类型 0-所有  1-电子保单常见问题 2-理赔分部地址 3-承保类咨询 4-理赔手续问题 5-交强险常见问题 6-自主查询
			* @param questionType
			* @param searchContent
			* @return
			* @author zhangjian2017年11月7日 上午9:47:36
	 */
	@RequestMapping(value={"/zzkfQuery.do"})
	public ModelAndView zzkfQuery(String questionType,String searchContent,ModelAndView modelAndView) {
		
		List<ZzkfWxSelfHelp> list = null;
		if ("0".equals(questionType)) {
			//搜索
			list = kfSelfService.searchQuestionList(searchContent);
		}else {
			list = kfSelfService.getQuestionList(questionType);
		}
		modelAndView.setViewName("kf/selfservice/zzkf/questionsDetail");
		modelAndView.addObject("questionList", list);
		modelAndView.addObject("questionType", questionType);
		modelAndView.addObject("searchContent", searchContent);
		return modelAndView;
	}
	
	/**
	 * 
			* 描述:根据问题序号--查询问题答案
			* @param questionId
			* @param modelAndView
			* @return
			* @author zhangjian2017年11月8日 上午10:49:57
	 */
	@RequestMapping(value={"/answerQuery.do"})
	public ModelAndView answerQuery(String questionId,ModelAndView modelAndView) {
		
		List<ZzkfWxSelfHelp> list = null;
		list = kfSelfService.searchAnswerList(questionId);
		modelAndView.setViewName("kf/selfservice/zzkf/answersDetail");
		modelAndView.addObject("answerList", list);
		return modelAndView;
	}

}
