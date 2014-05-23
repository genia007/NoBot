package edu.cmu.gizmo.unittest;

import junit.framework.TestCase;
import edu.cmu.gizmo.management.taskmanager.TaskExecutor.TaskType;
import edu.cmu.gizmo.management.taskmanager.TaskManager.TaskRequester;
import edu.cmu.gizmo.management.taskmanager.TaskReservation;

public class TestTaskReservation extends TestCase {
	
	public void testShouldDisinguishBetweenTwoDifferentReservations() {
		
		TaskReservation  rsvp1 = 
			new TaskReservation("Find Jane", 1, TaskRequester.TASK_CLIENT,null,TaskType.LOGIC_TASK,"leg4.xml");
		
		TaskReservation  rsvp2 = 
			new TaskReservation("Find Jane", 1, TaskRequester.TASK_CLIENT,null,TaskType.LOGIC_TASK,"leg4.xml");
		
		assertTrue(rsvp1.compareTo(rsvp2) ==0 );
		
		TaskReservation  rsvp3 = 
			new TaskReservation("Find Janez", 1, TaskRequester.TASK_CLIENT,null,TaskType.LOGIC_TASK,"leg4.xml");
		
		assertTrue(rsvp1.compareTo(rsvp3) != 0 );
		
		TaskReservation  rsvp4 = 
			new TaskReservation("Find Jane", 1, TaskRequester.TASK_MANAGER, null,TaskType.LOGIC_TASK,"leg4.xml");
		
		assertTrue(rsvp1.compareTo(rsvp4) != 0 );
		
		TaskReservation  rsvp5 = 
			new TaskReservation("Find Jane", 1, TaskRequester.TASK_CLIENT, null,TaskType.SCRIPT_TASK,"leg4.xml");
		
		assertTrue(rsvp1.compareTo(rsvp4) != 0 );
			
	}

}
