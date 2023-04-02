package org.demo.cpm.services;
import org.demo.cpm.repositories.CriticalPathRepository;
import org.demo.dto.web.model.CriticalPathDto;
import org.demo.dto.web.model.TaskDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private WebClient webClient;
    private final CriticalPathRepository criticalPathRepositoryService;
    private List<String> criticalTasks = new ArrayList<>();
    private int totalProjectDay = -1;
    private List<CriticalPathDto> criticalPathDtoList = new ArrayList<CriticalPathDto>();
    private Map<String, List<String>> successorsMap = new HashMap<>();
    public TaskServiceImpl(WebClient.Builder webClientBuilder, CriticalPathRepository criticalPathRepositoryService) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8085/api/v1/task").build();
        this.criticalPathRepositoryService = criticalPathRepositoryService;
    }

    @Override
    public List<String> getCriticalTasks() {
        criticalTasks.clear();
        List<TaskDto> taskDtoList = getTasksList();
        populateSuccessorsList(taskDtoList);
        calculateForwardPass(taskDtoList);
        calculateBackwardPass();
        calculateSlack();
        calculateCriticalPath();
        saveCriticalPathValue();
        return criticalTasks;
    }

    @Override
    public int getTotalProjectDays() {
        criticalTasks.clear();
        List<TaskDto> taskDtoList = getTasksList();
        populateSuccessorsList(taskDtoList);
        calculateForwardPass(taskDtoList);
        calculateBackwardPass();
        calculateSlack();
        calculateCriticalPath();
        saveCriticalPathValue();
        return totalProjectDay;
    }

    private List<TaskDto> getTasksList(){
        List<TaskDto> taskDtoList = this.webClient.get()
                .uri("/all")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TaskDto>>() {})
                .block();
        return taskDtoList;
    }
    private TaskDto getTaskById(String id){
        TaskDto taskDtoResponse =  this.webClient.get()
                .uri("/task/"+id)
                .retrieve()
                .bodyToMono(TaskDto.class)
                .block();
        return taskDtoResponse;
    }
    private void calculateCriticalPath() {
        for (CriticalPathDto criticalPathDto : criticalPathDtoList){
            if(criticalPathDto.getFloatStart()==0){
                criticalTasks.add(criticalPathDto.getId());
                totalProjectDay = criticalPathDto.getEarlyFinish();
            }
        }
    }

    private void calculateSlack() {
        for(CriticalPathDto cpDto: criticalPathDtoList) {
//            Slack = LS – ES = LF – EF
            int slackStart = cpDto.getLateStart() - cpDto.getEarlyStart();
            int slackFinish = cpDto.getLateFinish() - cpDto.getEarlyFinish();
            cpDto.setFloatStart(slackStart);
            cpDto.setFloatStart(slackFinish);
        }
    }

    private void populateSuccessorsList(List<TaskDto> taskDtoList) {
        for(TaskDto taskDto: taskDtoList) {
            String id = taskDto.getTaskId();
            List<String> childList = getSuccessorsListById(taskDtoList, id);
            successorsMap.put(id, childList);
        }
    }

    private List<String> getSuccessorsListById(List<TaskDto> taskDtoList, String id) {
        List<String> childList = new ArrayList<>();
        List<TaskDto> taskDtoList1 = taskDtoList
                .stream()
                .filter( tdto -> tdto.getDependentTaskId().contains(id))
                .collect( Collectors.toList());

        for(TaskDto taskDto: taskDtoList1) {
            childList.add(taskDto.getTaskId());
        }
        return childList;
    }


    private void saveCriticalPathValue() {
        criticalPathRepositoryService.saveAll(criticalPathDtoList);
    }

    private Optional<List<String>> getDependencyList(String id) {
        Optional<TaskDto> taskDto = Optional.ofNullable(getTaskById(id));
        return Optional.ofNullable(taskDto.get().getDependentTaskId());
    }

    private void calculateForwardPass(List<TaskDto> taskDtoList) {
        for (TaskDto taskDto:taskDtoList) {
            int es, ef;
           if(taskDto.getDependentTaskId().size() == 0){
               es = 0;
           } else {
               es = getEarlyStartTime(taskDto);
           }
            ef = es + taskDto.getRequiredDay();
            CriticalPathDto cpm = new CriticalPathDto(taskDto.getTaskId(), es, ef, taskDto.getRequiredDay());
           criticalPathDtoList.add(cpm);
        };
    }
    private int getEarlyStartTime(TaskDto taskDto) {
        List<String> dependentId = taskDto.getDependentTaskId();
        int es = 0;
        for (String id : dependentId) {
            int ef = getEarlyFinishTimeById(id);
                es = Math.max(es, ef);
        }
        return es;
    }

    private void calculateBackwardPass() {
        for(int i = criticalPathDtoList.size()-1; i>=0; i--){
            int lf, ls;
            if(criticalPathDtoList.get(i).getId().equals("END")){
                lf = criticalPathDtoList.get(i).getEarlyFinish();
            } else {
                List<String> successorList = successorsMap.get(criticalPathDtoList.get(i).getId());
                lf = getLateFinishTime(successorList);
            }
            ls = lf - criticalPathDtoList.get(i).getDuration();
            int finalI = i;
            criticalPathDtoList.stream()
                    .filter(obj->obj.getId().equals(criticalPathDtoList.get(finalI).getId()))
                    .findFirst()
                    .ifPresent(o-> {o.setLateStart(ls); o.setLateFinish(lf);});
        }
    }

    private int getLateFinishTime(List<String> successorList) {
        int lf = 999999;
        for(String id : successorList) {
            int ls = getLateStartTimeById(id);
            if(ls>=0) {
                lf = Math.min(lf, ls);
            }
        }
        return lf;
    }

    private int getEarlyFinishTimeById(String id) {
        List<CriticalPathDto> cpmObjList =  criticalPathDtoList.stream()
                .filter( criticalPath -> criticalPath.getId().equals(id))
                .collect( Collectors.toList());
        return cpmObjList.get(0).getEarlyFinish();
    }
    private int getLateStartTimeById(String id) {
        List<CriticalPathDto> cpmObjList =  criticalPathDtoList.stream()
                .filter( criticalPath -> criticalPath.getId().equals(id))
                .collect( Collectors.toList());
        return cpmObjList.get(0).getLateStart();
    }
}