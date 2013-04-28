package grotrack



import org.junit.*
import grails.test.mixin.*

@TestFor(AthleteController)
@Mock(Athlete)
class AthleteControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/athlete/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.athleteInstanceList.size() == 0
        assert model.athleteInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.athleteInstance != null
    }

    void testSave() {
        controller.save()

        assert model.athleteInstance != null
        assert view == '/athlete/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/athlete/show/1'
        assert controller.flash.message != null
        assert Athlete.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/athlete/list'

        populateValidParams(params)
        def athlete = new Athlete(params)

        assert athlete.save() != null

        params.id = athlete.id

        def model = controller.show()

        assert model.athleteInstance == athlete
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/athlete/list'

        populateValidParams(params)
        def athlete = new Athlete(params)

        assert athlete.save() != null

        params.id = athlete.id

        def model = controller.edit()

        assert model.athleteInstance == athlete
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/athlete/list'

        response.reset()

        populateValidParams(params)
        def athlete = new Athlete(params)

        assert athlete.save() != null

        // test invalid parameters in update
        params.id = athlete.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/athlete/edit"
        assert model.athleteInstance != null

        athlete.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/athlete/show/$athlete.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        athlete.clearErrors()

        populateValidParams(params)
        params.id = athlete.id
        params.version = -1
        controller.update()

        assert view == "/athlete/edit"
        assert model.athleteInstance != null
        assert model.athleteInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/athlete/list'

        response.reset()

        populateValidParams(params)
        def athlete = new Athlete(params)

        assert athlete.save() != null
        assert Athlete.count() == 1

        params.id = athlete.id

        controller.delete()

        assert Athlete.count() == 0
        assert Athlete.get(athlete.id) == null
        assert response.redirectedUrl == '/athlete/list'
    }
}
