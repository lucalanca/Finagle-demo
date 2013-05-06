namespace java com.twitter.demo


/*******************************************
 *********** DOMAIN DATA *******************
 ******************************************/
enum BettingType {
    ODDS
}

enum MarketType {
	MATCH_ODDS
}

struct MarketDescriptor {
	1: BettingType bettingType
	2: bool bspMarket
	3: string marketName
	4: string marketTime
	5: MarketType marketType
	6: bool persistenceEnabled
	7: string suspendTime
	8: bool turnInPlayEnabled
}

struct RunnerMetadata {
	1: string runnerId
}

struct RunnerDescriptor {
	1: string runnerName
	2: RunnerMetadata metadata
}

struct ExchangeItem {
	1: double price
	2: double size
}

struct Exchange {
	1: list<ExchangeItem> availableToBack
	2: list<ExchangeItem> availableToLay
}

enum RunnerStateStatus {
	ACTIVE
}

struct RunnerState {
	1: i16 sortPriority
	2: RunnerStateStatus status
	3: i16 totalMatched
}

struct Runner {
	1: RunnerDescriptor decription
	2: Exchange exchange
	3: i16 handicap
	4: i16 selectionId
	5: RunnerState state

}

enum MarketStatus {
	ACTIVE
}

struct MarketState {
	1: i16 betDelay
	2: bool bspReconciled
	3: bool complete
	4: bool crossMatching
	5: bool inplay
	6: i16 numberOfActiveRunners
	7: i16 numberOfRunners
	8: i16 numberOfWinners
	9: bool runnersVoidable
	10: MarketStatus status
	11: double totalAvailable
	12: double totalMatched
	13: i32 version
}

service MarketStateService {
  MarketState getState()
}

struct MarketNode {
	1: string id
	2: MarketDescriptor description
	3: bool isMarketDataDelayed
	4: MarketState state

}

enum CountryCode {
	ES
}

struct Event {
	1: CountryCode countryCode
	2: string eventName
	3: string openDate
	4: string timezone

}

struct EventNode {
	1: Event event
	2: i64 eventId
	3: list<MarketNode> marketNodes
}

struct EventType {
	1: list<EventNode> eventNodes
	2: i16 eventTypeId
}

struct Html {
	1: string content
}


service ModuleService {
    Html render()
}

service PageService {
    Html render()
}

service ModulesRepositoryService {
    Html moduleFor(1: string name)
}
