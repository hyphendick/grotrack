<%@ page import="grotrack.Trainer" %>



<div class="fieldcontain ${hasErrors(bean: trainerInstance, field: 'athletes', 'error')} ">
	<label for="athletes">
		<g:message code="trainer.athletes.label" default="Athletes" />
		
	</label>
	<g:select name="athletes" from="${grotrack.Athlete.list()}" multiple="multiple" optionKey="id" size="5" value="${trainerInstance?.athletes*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: trainerInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="trainer.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${trainerInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: trainerInstance, field: 'preferredName', 'error')} ">
	<label for="preferredName">
		<g:message code="trainer.preferredName.label" default="Preferred Name" />
		
	</label>
	<g:textField name="preferredName" value="${trainerInstance?.preferredName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: trainerInstance, field: 'teams', 'error')} ">
	<label for="teams">
		<g:message code="trainer.teams.label" default="Teams" />
		
	</label>
	<g:select name="teams" from="${grotrack.Team.list()}" multiple="multiple" optionKey="id" size="5" value="${trainerInstance?.teams*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: trainerInstance, field: 'username', 'error')} ">
	<label for="username">
		<g:message code="trainer.username.label" default="Username" />
		
	</label>
	<g:textField name="username" value="${trainerInstance?.username}"/>
</div>

