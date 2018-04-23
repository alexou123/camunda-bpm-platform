/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.client.util;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;

public class ProcessModels {

  public static final String PROCESS_KEY = "process";
  public static final String EXTERNAL_TASK_ID = "externalTask";
  public static final String EXTERNAL_TASK_ONE_ID = "externalTask1";
  public static final String EXTERNAL_TASK_TWO_ID = "externalTask2";
  public static final String USER_TASK_ID = "userTask";
  public static final String USER_TASK_AFTER_BPMN_ERROR = "userTaskAfterBpmnError";
  public static final String EXTERNAL_TASK_TOPIC_FOO = "foo";
  public static final String EXTERNAL_TASK_TOPIC_BAR = "bar";

  public static ProcessBuilder newModel() {
    return newModel(PROCESS_KEY);
  }

  public static ProcessBuilder newModel(String processKey) {
    return Bpmn.createExecutableProcess(processKey);
  }

  public static final BpmnModelInstance ONE_EXTERNAL_TASK_PROCESS =
      newModel()
      .startEvent("startEvent")
      .serviceTask(EXTERNAL_TASK_ID)
        .camundaExternalTask(EXTERNAL_TASK_TOPIC_FOO)
      .endEvent("endEvent")
      .done();

  public static final BpmnModelInstance TWO_EXTERNAL_TASK_PROCESS =
      newModel()
      .startEvent("startEvent")
      .serviceTask(EXTERNAL_TASK_ONE_ID)
        .camundaExternalTask(EXTERNAL_TASK_TOPIC_FOO)
      .serviceTask(EXTERNAL_TASK_TWO_ID)
        .camundaExternalTask(EXTERNAL_TASK_TOPIC_BAR)
      .endEvent("endEvent")
      .done();

  public static final BpmnModelInstance ONE_EXTERNAL_TASK_AND_ONE_TASK_PROCESS =
      newModel()
      .startEvent("startEvent")
      .serviceTask(EXTERNAL_TASK_ID)
        .camundaExternalTask(EXTERNAL_TASK_TOPIC_FOO)
      .userTask(USER_TASK_ID)
      .endEvent("endEvent")
      .done();

  public static final BpmnModelInstance BPMN_ERROR_EXTERNAL_TASK_PROCESS =
      newModel()
      .startEvent()
      .serviceTask(EXTERNAL_TASK_ID)
        .camundaExternalTask(EXTERNAL_TASK_TOPIC_FOO)
      .userTask(USER_TASK_ID)
      .endEvent()
      .moveToActivity(EXTERNAL_TASK_ID)
      .boundaryEvent()
        .error("500")
      .userTask(USER_TASK_AFTER_BPMN_ERROR)
      .endEvent()
      .done();

}