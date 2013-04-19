<%@ page import="grotrack.Frequency" %>



<div class="fieldcontain ${hasErrors(bean: frequencyInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="frequency.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${frequencyInstance?.name}"/>
</div>

