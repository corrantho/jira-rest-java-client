/*
 * Copyright (C) 2012 Atlassian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.atlassian.jira.rest.client.internal.json;

import com.atlassian.jira.rest.client.domain.BasicProject;
import com.atlassian.jira.rest.client.domain.CreateIssueIssueType;
import com.atlassian.jira.rest.client.domain.CreateIssueMetadataProject;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

/**
 * JSON parser for CreateIssueMetadataProject
 *
 * @since v1.0
 */
public class CreateIssueMetadataProjectJsonParser implements JsonParser<CreateIssueMetadataProject> {

	private final JsonArrayParser<Iterable<CreateIssueIssueType>> issueTypesParser = GenericJsonArrayParser.create(new CreateIssueIssueTypeJsonParser());

	private final BasicProjectJsonParser basicProjectJsonParser = new BasicProjectJsonParser();

	@Override
	public CreateIssueMetadataProject parse(final JSONObject json) throws JSONException {
		final BasicProject basicProject = basicProjectJsonParser.parse(json);
		final JSONArray issueTypesArray = json.optJSONArray("issuetypes");
		final Iterable<CreateIssueIssueType> issueTypes = (issueTypesArray != null) ?
				issueTypesParser.parse(issueTypesArray) : Collections.<CreateIssueIssueType>emptyList();

		final Map<String, URI> avatarUris = JsonParseUtil.getAvatarUris(json.getJSONObject("avatarUrls"));
		return new CreateIssueMetadataProject(basicProject.getSelf(), basicProject.getKey(),
				basicProject.getName(), avatarUris, issueTypes);
	}
}