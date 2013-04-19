<%@ page import="grotrack.Workout" %>



<div class="fieldcontain ${hasErrors(bean: workoutInstance, field: 'endDate', 'error')} required">
	<label for="endDate">
		<g:message code="workout.endDate.label" default="End Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="endDate" precision="day"  value="${workoutInstance?.endDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: workoutInstance, field: 'exercises', 'error')} ">
	<label for="exercises">
		<g:message code="workout.exercises.label" default="Exercises" />
		
	</label>
	<g:select name="exercises" from="${grotrack.Exercise.list()}" multiple="multiple" optionKey="id" size="5" value="${workoutInstance?.exercises*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: workoutInstance, field: 'frequency', 'error')} required">
	<label for="frequency">
		<g:message code="workout.frequency.label" default="Frequency" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="frequency" name="frequency.id" from="${grotrack.Frequency.list()}" optionKey="id" required="" value="${workoutInstance?.frequency?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: workoutInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="workout.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${workoutInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: workoutInstance, field: 'startDate', 'error')} required">
	<label for="startDate">
		<g:message code="workout.startDate.label" default="Start Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="startDate" precision="day"  value="${workoutInstance?.startDate}"  />
</div>

