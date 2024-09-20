//
//  Created by Lukáš Matuška on 04.09.2024
//  Copyright © 2024 Matee. All rights reserved.
//

public enum ViewData<Data: Equatable>: Equatable {
    
    public enum EmptyReason: Equatable {
        case noData
        case search
    }
    
    case data(Data)
    case error(Error)
    case loading(mock: Data)
    case empty(EmptyReason)
    
    public var isLoading: Bool {
        switch self {
        case .loading: true
        default: false
        }
    }
    
    public var isError: Bool {
        switch self {
        case .error: true
        default: false
        }
    }
    
    public var hasData: Bool {
        switch self {
        case .data: true
        default: false
        }
    }
    
    public var data: Data? {
        switch self {
        case let .data(data): data
        default: nil
        }
    }
    
    public static func == (lhs: ViewData<Data>, rhs: ViewData<Data>) -> Bool {
        switch (lhs, rhs) {
        case let (.data(ldata), .data(rdata)): ldata == rdata
        case let (.error(lerror), .error(rerror)): lerror.localizedDescription == rerror.localizedDescription
        case let (.loading(ldata), .loading(rdata)): ldata == rdata
        case let (.empty(lreason), .empty(rreason)): lreason == rreason
        default: false
        }
    }
}
