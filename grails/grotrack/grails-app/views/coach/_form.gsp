<%@ page import="grotrack.Coach" %>



<div class="fieldcontain ${hasErrors(bean: coachInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="coach.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${coachInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: coachInstance, field: 'preferredName', 'error')} ">
	<label for="preferredName">
		<g:message code="coach.preferredName.label" default="Preferred Name" />
		
	</label>
	<g:textField name="preferredName" value="${coachInstance?.preferredName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: coachInstance, field: 'username', 'error')} ">
	<label for="username">
		<g:message code="coach.username.label" default="Username" />
		
	</label>
	<g:textField name="username" value="${coachInstance?.username}"/>
</div>

