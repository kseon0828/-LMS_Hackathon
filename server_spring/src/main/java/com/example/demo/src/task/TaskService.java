package com.example.demo.src.task;

import com.example.demo.config.BaseException;
import com.example.demo.src.todo.model.PostTodoReq;
import com.example.demo.src.todo.model.PostTodoRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class TaskService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TaskDao todoDao;
    private final TaskProvider taskProvider;
    private final JwtService jwtService;


    @Autowired
    public TaskService(TaskDao todoDao, TaskProvider taskProvider, JwtService jwtService) {
        this.todoDao = todoDao;
        this.taskProvider = taskProvider;
        this.jwtService = jwtService;

    }

    // todo리스트 생성
    //public PostTodoRes createTodo(int userIdx, Date date, PostTodoReq postTodoReq) throws BaseException {
    public PostTodoRes createTodo(int userIdx, Date date, String postTodoReq) throws BaseException {

        try{
            //int todoIdx = todoDao.insertTodo(userIdx, date, postTodoReq.getTodoName());
            int todoIdx = todoDao.insertTodo(userIdx, date, postTodoReq);
            return new PostTodoRes(todoIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // todo리스트 수정
    public void modifyTodo(int userIdx, int todoIdx, PostTodoReq postTodoReq) throws BaseException {

        try{
            //todoDao가 잘 실행되면 1, 아니면 0을 전달 받아 error 코드 표시
            int result = todoDao.updateTodo(userIdx, todoIdx, postTodoReq.getTodoName());

            if(result == 0){
                throw new BaseException(MODIFY_FAIL_POST);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //todo리스트 삭제
    public void deleteTodo(int todoIdx) throws BaseException{
        try{
            //todoDao가 잘 실행되면 1, 아니면 0을 전달 받아 error 코드 표시
            int result = todoDao.deleteTodo(todoIdx);

            if(result == 0){
                throw new BaseException(DELETE_FAIL_POST);
            }
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}