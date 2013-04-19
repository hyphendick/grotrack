<%@ page import="grotrack.Team" %>



<div class="fieldcontain ${hasErrors(bean: teamInstance, field: 'athletes', 'error')} ">
	<label for="athletes">
		<g:message code="team.athletes.label" default="Athletes" />
		
	</label>
	<g:select name="athletes" from="${grotrack.Athlete.list()}" multiple="multiple" optionKey="id" size="5" value="${teamInstance?.athletes*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: teamInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="team.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${teamInstance?.name}"/>
</div>

