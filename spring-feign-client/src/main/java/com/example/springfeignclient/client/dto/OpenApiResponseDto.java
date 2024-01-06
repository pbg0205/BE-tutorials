package com.example.springfeignclient.client.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class OpenApiResponseDto {

	private HeaderResponseDto header;
	private BodyResponseDto body;

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@ToString
	private static class HeaderResponseDto {
		@JsonProperty("resultCode")
		private String resultCode;

		@JsonProperty("resultMsg")
		private String resultMessage;
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@ToString
	private static class BodyResponseDto {
		private int pageNo;
		private int totalCount;
		private int numOfRows;
		private List<ItemResponseDto> items;

		@NoArgsConstructor
		@AllArgsConstructor
		@Getter
		@ToString
		private static class ItemResponseDto {
			@JsonProperty("TYPE_NAME")
			private String typeName;

			@JsonProperty("MIX_TYPE")
			private String mixType;

			@JsonProperty("INGR_CODE")
			private String ingrCode;

			@JsonProperty("INGR_ENG_NAME")
			private String ingrEngName;

			@JsonProperty("INGR_NAME")
			private String ingrName;

			@JsonProperty("MIX_INGR")
			private String mixIngr;

			@JsonProperty("FORM_NAME")
			private String formName;

			@JsonProperty("ITEM_SEQ")
			private String itemSeq;

			@JsonProperty("ITEM_NAME")
			private String itemName;

			@JsonProperty("ITEM_PERMIT_DATE")
			private String itemPermitDate;

			@JsonProperty("ENTP_NAME")
			private String entpName;

			@JsonProperty("CHART")
			private String chart;

			@JsonProperty("CLASS_CODE")
			private String classCode;

			@JsonProperty("CLASS_NAME")
			private String className;

			@JsonProperty("ETC_OTC_NAME")
			private String etcOtcName;

			@JsonProperty("MAIN_INGR")
			private String mainIngr;

			@JsonProperty("NOTIFICATION_DATE")
			private String notificationDate;

			@JsonProperty("PROHBT_CONTENT")
			private String prohibitContent;

			@JsonProperty("REMARK")
			private String remark;

			@JsonProperty("INGR_ENG_NAME_FULL")
			private String ingrEngNameFull;

			@JsonProperty("CHANGE_DATE")
			private String changeDate;
		}
	}
}
