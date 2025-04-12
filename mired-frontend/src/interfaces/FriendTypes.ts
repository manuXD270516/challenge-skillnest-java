export interface FriendDto {
    id: number;
    email: string;
    status: 'PENDING' | 'ACCEPTED' | 'REJECTED';
  }
  
  export interface FriendRequestDto {
    requestId: number;
    senderEmail: string;
  }
  