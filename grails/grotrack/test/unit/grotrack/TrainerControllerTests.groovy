package grotrack



import org.junit.*
import grails.test.mixin.*

@TestFor(TrainerController)
@Mock(Trainer)
class TrainerControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/trainer/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.trainerInstanceList.size() == 0
        assert model.trainerInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.trainerInstance != null
    }

    void testSave() {
        controller.save()

        assert model.trainerInstance != null
        assert view == '/trainer/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/trainer/show/1'
        assert controller.flash.message != null
        assert Trainer.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/trainer/list'

        populateValidParams(params)
        def trainer = new Trainer(params)

        assert trainer.save() != null

        params.id = trainer.id

        def model = controller.show()

        assert model.trainerInstance == trainer
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/trainer/list'

        populateValidParams(params)
        def trainer = new Trainer(params)

        assert trainer.save() != null

        params.id = trainer.id

        def model = controller.edit()

        assert model.trainerInstance == trainer
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/trainer/list'

        response.reset()

        populateValidParams(params)
        def trainer = new Trainer(params)

        assert trainer.save() != null

        // test invalid parameters in update
        params.id = trainer.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/trainer/edit"
        assert model.trainerInstance != null

        trainer.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/trainer/show/$trainer.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        trainer.clearErrors()

        populateValidParams(params)
        params.id = trainer.id
        params.version = -1
        controller.update()

        assert view == "/trainer/edit"
        assert model.trainerInstance != null
        assert model.trainerInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/trainer/list'

        response.reset()

        populateValidParams(params)
        def trainer = new Trainer(params)

        assert trainer.save() != null
        assert Trainer.count() == 1

        params.id = trainer.id

        controller.delete()

        assert Trainer.count() == 0
        assert Trainer.get(trainer.id) == null
        assert response.redirectedUrl == '/trainer/list'
    }
}
