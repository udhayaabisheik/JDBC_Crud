package com.demo.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.demo.crud.dao.CrudDao;
import com.demo.crud.model.CrudModel;
import com.demo.crud.model.Response;

@CrossOrigin(origins = "*", allowedHeaders = "*") // to avoid cors error
@RestController // this notify that we are using restful api guidelines
@RequestMapping("/signup") // used to map web request on to specific handler classes or handler method
public class CrudController {

	@Autowired
	private CrudDao dao; // use to create object to the specific class without using new keyword

	@PostMapping("/insert")
	public ResponseEntity<Response> insertDetails(@RequestBody CrudModel input) {
		return ResponseEntity.ok(dao.insertUser(input));
	}

	@GetMapping("/getAllUser")
	public ResponseEntity<Response> getDetails() {
		return ResponseEntity.ok(dao.getAllUser());
	}

	@GetMapping("/getUserById")
	public ResponseEntity<Response> getOneDetail(@RequestParam String id) {
		return ResponseEntity.ok(dao.getUserById(id));
	}

	@PutMapping("/updateUserDetail")
	public ResponseEntity<Response> updateUserDetail(@RequestParam String email, @RequestParam String id) {
		return ResponseEntity.ok(dao.updateUserDetail(email, id));
	}

	@DeleteMapping("/deleteUser")
	public ResponseEntity<Response> deleteOneDetail(@RequestParam String id) {
		return ResponseEntity.ok(dao.deleteUser(id));
	}

	@PostMapping("/send/email")
	public ResponseEntity<Response> sendEmail(@RequestBody CrudModel emailProcess) {
		return ResponseEntity.ok(dao.sendEmail(emailProcess));
	}

}
