package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import models.Baby;
import models.BodyIndex;
import models.GradeForm;
import models.User;
import play.db.jpa.JPA;
import beans.BIndexBean;
import beans.GradeBean;

import com.sudocn.play.BasicModel;

/**
 * 与客户端交互专用控制器
 * 
 * @author Tanshichang
 * @since 2015-2-10
 */
public class FgAction extends WebService {

	/**
	 * 保存实体类并返回JSONP回调结果
	 * 
	 * @param model
	 */
	protected static void saveModel(BasicModel model) {
		String result = "";
		if (model != null) {
			try {
				model.save();
				result = "success";
			} catch (Exception e) {
				e.printStackTrace();
				result = "fail";
			}
		} else {
			result = "fail";
		}
		wsOkAsJsonP(result);
	}

}