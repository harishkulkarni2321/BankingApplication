package com.Banking.BankingAppl.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@Controller
@RequestMapping("/customerService")
public class CustomerService {

    private long counter;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/updatepassbookservice/{customerid}/{tokenid}")
    public ResponseEntity<String> updatePassBookService(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<String>(headers);

       //String tokenList =  restTemplate.exchange("http://localhost:8080/generateToken", HttpMethod.GET, entity, String.class).getBody();
         List<Integer> tokenList =  new ArrayList<>();
        tokenList.add(1);
        tokenList.add(2);
        try {
            executeAsynchronously();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("counter1:"+counter);
        //tokenList.add((int) (long) counter); //added for testing

        //checkExecutingSequentailly(tokenList);

        for (Integer token:tokenList){
            System.out.println("token:"+token);
            System.out.println("counter:"+counter);
            if(Long.toString(counter).equals(token)){
                System.out.println("Assign token to counter");
                tokenList.remove(token);
            }
        }

        return new ResponseEntity<String>("Updated Passbook Fulfilled service successfuly", HttpStatus.OK);
    }

    @RequestMapping("/cashDeposit/{customerid}")
    public ResponseEntity<String> cashdeposit(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<String>(headers);

        //String token =  restTemplate.exchange("http://localhost:8080/generateToken", HttpMethod.GET, entity, String.class).getBody();

        try {
            executeAsynchronously();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("counter2:----->"+counter);
        return new ResponseEntity<String>("Cash depositted succesfully",HttpStatus.OK);
    }

    @RequestMapping("/demandDraft/{customerid}")
    public ResponseEntity<String> demandDraft(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<String>(headers);

       // String token =  restTemplate.exchange("http://localhost:8080/generateToken", HttpMethod.GET, entity, String.class).getBody();
      try {
          executeAsynchronously();
      }catch (InterruptedException e) {
          e.printStackTrace();
      }
        System.out.println("counter3:"+counter);
        return new ResponseEntity<>("demand drafted succesfully",HttpStatus.OK);
    }

    public void checkExecutingSequentailly(List<Integer> token) {
        ExecutorService service = Executors.newFixedThreadPool(1);
        List<Future<Runnable>> futures = new ArrayList<Future<Runnable>>();


         List<Integer> tokenList =new ArrayList<>();
         for(int i=0;i<tokenList.size();i++){
             Future f = service.submit(new Runnable() {
                 @Override
                 public void run() {
                     //processing token sequentailly
                 }
             });
             futures.add(f);
         }
         //shut down the executor service so that this thread can exit
        service.shutdownNow();
    }

    public void executeAsynchronously() throws  InterruptedException{
        final ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    counter = (long) ((Math.random() + 0.1) * 50);

                }
            }
        });

        if (executor.awaitTermination(2, TimeUnit.SECONDS)) {
            System.out.println("task completed");
        } else {
            System.out.println("Forcing shutdown...");
            executor.shutdownNow();
        }

    }



}
