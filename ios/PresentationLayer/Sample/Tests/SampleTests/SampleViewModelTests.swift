import Factory
import KMPShared
@testable import Sample
import SharedDomainMocks
import Testing
import UIToolkit

@MainActor
@Suite("Sample view model tests")
struct SampleViewModelTests {
    
    // MARK: Dependencies
    let getSampleTextUseCase: GetSampleTextUseCaseMock
    let flowController: FlowControllerMock<SampleFlow>
    
    // MARK: Test subject
    var viewModel: SampleViewModel! // swiftlint:disable:this implicitly_unwrapped_optional
    
    init() {
        // Init dependencies
        flowController = FlowControllerMock(navigationController: .init())
        getSampleTextUseCase = GetSampleTextUseCaseMock()
        
        // Register dependencies
        Container.shared.reset()
        Container.shared.getSampleTextUseCase.register { [self] in
            getSampleTextUseCase
        }
        
        // Init test subject
        viewModel = SampleViewModel(flowController: flowController)
    }
    
    // MARK: Tests
    
    @Test func testOnAppearSuccess() async throws {
        
        // Arrange
        getSampleTextUseCase.executeReturnValue = ResultSuccess(data: SampleText.stub)
        
        // Act
        viewModel.onAppear()
        await viewModel.awaitAllTasks()
        
        // Validate
        #expect(getSampleTextUseCase.executeCallsCount == 1, "GetSampleTextUseCase must be called on appear")
        #expect(viewModel.state.sampleText == .data(SampleText.stub), "Sample text must be set properly")
    }
    
    @Test func testOnAppearFailure() async throws {
        
        // Arrange
        let error = KmmLocalizedError(errorResult: nil, localizedMessage: "test")
        getSampleTextUseCase.executeThrowableError = error
        
        // Act
        viewModel.onAppear()
        await viewModel.awaitAllTasks()
        
        // Validate
        #expect(getSampleTextUseCase.executeCallsCount == 1, "GetSampleTextUseCase must be called on appear")
        #expect(viewModel.state.sampleText == .error(error), "Error must be set properly")
    }
    
    @Test func testOnButtonTapped() async throws {
        
        // Arrange
        // Nothing to do here
        
        // Act
        viewModel.onIntent(.onButtonTapped)
        await viewModel.awaitAllTasks()
        
        // Validate
        #expect(viewModel.state.toast != nil, "Toast must be presented")
    }
    
    @Test func testOnToastChanged() async throws {
        
        // Arrange
        let toast = ToastData("test", hideAfter: 2)
        
        // Act
        viewModel.onIntent(.onToastChanged(data: toast))
        await viewModel.awaitAllTasks()
        
        // Validate
        #expect(viewModel.state.toast == toast, "Toast must be set properly")
    }
}
