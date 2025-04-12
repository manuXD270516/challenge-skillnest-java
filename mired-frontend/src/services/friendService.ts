import axios from "../api/axiosInstance";
import { FriendDto, FriendRequestDto } from "../interfaces/FriendTypes";

export const searchUsers = async (query: string): Promise<FriendDto[]> => {
  const response = await axios.get(`/api/friends/search?query=${query}`);
  return response.data;
};

export const sendFriendRequest = async (receiverId: number) => {
  return await axios.post("/api/friends/request", { receiverId });
};

export const acceptFriendRequest = async (requestId: number) => {
  return await axios.post(`/api/friends/accept/${requestId}`);
};

export const rejectFriendRequest = async (requestId: number) => {
  return await axios.post(`/api/friends/reject/${requestId}`);
};

export const fetchReceivedRequests = async (): Promise<FriendRequestDto[]> => {
  const response = await axios.get("/api/friends/requests/received");
  return response.data;
};

export const fetchFriendList = async (): Promise<FriendDto[]> => {
  const response = await axios.get("/api/friends/list");
  return response.data;
};

export const removeFriend = async (friendId: number) => {
  return await axios.delete(`/api/friends/${friendId}`);
};
