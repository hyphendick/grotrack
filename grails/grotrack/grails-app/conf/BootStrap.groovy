class BootStrap {

    def init = { servletContext ->
    	def workout1 = new Workout(name: 'workout 1').save()
        def workout2 = new Workout(name: 'Workout 2').save()
    }
    def destroy = {
    }
}
