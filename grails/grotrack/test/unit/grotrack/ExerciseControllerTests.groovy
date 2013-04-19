package grotrack



import org.junit.*
import grails.test.mixin.*

@TestFor(ExerciseController)
@Mock(Exercise)
class ExerciseControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/exercise/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.exerciseInstanceList.size() == 0
        assert model.exerciseInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.exerciseInstance != null
    }

    void testSave() {
        controller.save()

        assert model.exerciseInstance != null
        assert view == '/exercise/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/exercise/show/1'
        assert controller.flash.message != null
        assert Exercise.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/exercise/list'

        populateValidParams(params)
        def exercise = new Exercise(params)

        assert exercise.save() != null

        params.id = exercise.id

        def model = controller.show()

        assert model.exerciseInstance == exercise
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/exercise/list'

        populateValidParams(params)
        def exercise = new Exercise(params)

        assert exercise.save() != null

        params.id = exercise.id

        def model = controller.edit()

        assert model.exerciseInstance == exercise
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/exercise/list'

        response.reset()

        populateValidParams(params)
        def exercise = new Exercise(params)

        assert exercise.save() != null

        // test invalid parameters in update
        params.id = exercise.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/exercise/edit"
        assert model.exerciseInstance != null

        exercise.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/exercise/show/$exercise.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        exercise.clearErrors()

        populateValidParams(params)
        params.id = exercise.id
        params.version = -1
        controller.update()

        assert view == "/exercise/edit"
        assert model.exerciseInstance != null
        assert model.exerciseInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/exercise/list'

        response.reset()

        populateValidParams(params)
        def exercise = new Exercise(params)

        assert exercise.save() != null
        assert Exercise.count() == 1

        params.id = exercise.id

        controller.delete()

        assert Exercise.count() == 0
        assert Exercise.get(exercise.id) == null
        assert response.redirectedUrl == '/exercise/list'
    }
}
