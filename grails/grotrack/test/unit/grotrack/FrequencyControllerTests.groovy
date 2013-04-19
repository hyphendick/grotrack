package grotrack



import org.junit.*
import grails.test.mixin.*

@TestFor(FrequencyController)
@Mock(Frequency)
class FrequencyControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/frequency/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.frequencyInstanceList.size() == 0
        assert model.frequencyInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.frequencyInstance != null
    }

    void testSave() {
        controller.save()

        assert model.frequencyInstance != null
        assert view == '/frequency/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/frequency/show/1'
        assert controller.flash.message != null
        assert Frequency.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/frequency/list'

        populateValidParams(params)
        def frequency = new Frequency(params)

        assert frequency.save() != null

        params.id = frequency.id

        def model = controller.show()

        assert model.frequencyInstance == frequency
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/frequency/list'

        populateValidParams(params)
        def frequency = new Frequency(params)

        assert frequency.save() != null

        params.id = frequency.id

        def model = controller.edit()

        assert model.frequencyInstance == frequency
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/frequency/list'

        response.reset()

        populateValidParams(params)
        def frequency = new Frequency(params)

        assert frequency.save() != null

        // test invalid parameters in update
        params.id = frequency.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/frequency/edit"
        assert model.frequencyInstance != null

        frequency.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/frequency/show/$frequency.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        frequency.clearErrors()

        populateValidParams(params)
        params.id = frequency.id
        params.version = -1
        controller.update()

        assert view == "/frequency/edit"
        assert model.frequencyInstance != null
        assert model.frequencyInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/frequency/list'

        response.reset()

        populateValidParams(params)
        def frequency = new Frequency(params)

        assert frequency.save() != null
        assert Frequency.count() == 1

        params.id = frequency.id

        controller.delete()

        assert Frequency.count() == 0
        assert Frequency.get(frequency.id) == null
        assert response.redirectedUrl == '/frequency/list'
    }
}
