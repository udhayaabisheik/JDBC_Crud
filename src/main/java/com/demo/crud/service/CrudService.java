package com.demo.crud.service;

import org.springframework.stereotype.Component;
import com.demo.crud.model.CrudModel;
import com.demo.crud.model.Response;

@Component
public interface CrudService {

	public Response insertUser(CrudModel input);

	public Response getAllUser();

	public Response getUserById(String id);

	public Response updateUserDetail(String email, String id);

	public Response deleteUser(String id);

	public Response sendEmail(CrudModel emailProcess);

}
