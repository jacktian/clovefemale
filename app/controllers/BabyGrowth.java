package controllers;

import java.util.List;

import models.BodyIndex;
import models.GradeCondition;
import models.Vaccination;

import play.mvc.Controller;

public class BabyGrowth extends Controller {

	public static void listVaccinations() {
		List<Vaccination> vacclist = Vaccination.findAll();
		response.contentType = "application/json";
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		renderJSON(vacclist);
	}

	public static void listGradeConditions() {
		List<GradeCondition> gradelist = GradeCondition.findAll();
		response.contentType = "application/json";
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		renderJSON(gradelist);
	}

	public static void listBodyIndexs() {
		List<BodyIndex> indexlist = BodyIndex.findAll();
		response.contentType = "application/json";
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		renderJSON(indexlist);
	}

	public static void addVaccination(Vaccination v) {
		v.save();
		listVaccinations();
	}

	public static void addGradeCondition(GradeCondition grade) {
		grade.save();
		listGradeConditions();
	}

	public static void addBodyIndex(BodyIndex bodyIndex) {
		bodyIndex.save();
		listBodyIndexs();
	}

	public static void findBySubject(String subject) {
		List<GradeCondition> list = GradeCondition
				.find("select * from GradeCondition c where c.subject= ? order by date",
						subject).fetch();
		response.contentType = "application/json";
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		renderJSON(list);
	}

	public static void findAllBySubject(String subject) {
		List<GradeCondition> gradelist = GradeCondition
				.find("subject", subject).fetch();
		response.contentType = "application/json";
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		renderJSON(gradelist);
	}

	public static void findByHeight() {
		List<BodyIndex> list = GradeCondition.find("order by date").fetch();
		response.contentType = "application/json";
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		renderJSON(list);
	}

	public static void editBodyIndex(Long id) {
             BodyIndex bodyIndex=BodyIndex.findById(id);
             response.contentType = "application/json";
     		response.setHeader("Content-Type", "application/json;charset=UTF-8");
     		renderJSON(bodyIndex);
	}

	public static void editGradeCondition(Long id) {
            GradeCondition gradeCondition=GradeCondition.findById(id);
            response.contentType = "application/json";
     		response.setHeader("Content-Type", "application/json;charset=UTF-8");
     		renderJSON(gradeCondition);
	}

	public static void editVaccination(Long id) {
              Vaccination vaccination=Vaccination.findById(id);
              response.contentType = "application/json";
       		response.setHeader("Content-Type", "application/json;charset=UTF-8");
       		renderJSON(vaccination);
	}
}
