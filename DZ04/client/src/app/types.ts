export interface EstateData {
  id: number;
  description: string;
  estateOwnerId: number;
}

export interface StartProcessData {
  requestId: number;
  buyerId: string;
  estateId: number;
}

export interface NotificationData {
  id: number;
  message: string;
}

export interface AcceptTourData {
  pid: string;
  agentId: string;
}

export interface TourInfo {
  agentId?: string;
  agentName?: string;
  buyerId: string;
  buyerName: string;
  estateId: number;
  estateDescription: string;
  pid: string;
  requestId: number;
  startTime: string;
  termFits?: boolean;
  tourDateTime?: string;
}

export interface TermProposalData {
  taskId: string;
  tourDateTime: string;
}

export interface TermProposalOverviewTaskData {
  tid: string;
  pid: string;
  variables: {
    TourDateTime: string;
    agentId: string;
    agentName: string;
    buyerId: string;
    buyerName: string;
    estateId: number;
    estateDescription: string;
    requestId: number;
  };
}

export interface TermProposalTaskData extends TermProposalOverviewTaskData {}

export interface TermProposalAnswerData {
  taskId: string;
  termFits: boolean;
}
